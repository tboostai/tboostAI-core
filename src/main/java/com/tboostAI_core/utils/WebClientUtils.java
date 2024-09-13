package com.tboostAI_core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtils {

    private final WebClient.Builder internalWebClientBuilder;
    private final WebClient.Builder externalWebClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(WebClientUtils.class);

    public WebClientUtils(@Qualifier("createInternalWebClientBuilder") WebClient.Builder internalWebClientBuilder,
                          @Qualifier("createExternalWebClientBuilder") WebClient.Builder externalWebClientBuilder) {
        this.internalWebClientBuilder = internalWebClientBuilder;
        this.externalWebClientBuilder = externalWebClientBuilder;
    }

    public <T> Mono<T> sendGetRequestExternal(String uri, Class<T> responseType) {
        return externalWebClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client error occurred while requesting " + uri))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server error occurred while requesting " + uri))
                )
                .bodyToMono(responseType)
                .doOnError(WebClientResponseException.class, e -> logger.error("Error response: {}", e.getResponseBodyAsString()))
                .onErrorResume(e -> {
                    throw new RuntimeException("Failed to retrieve data from " + uri, e);
                });
    }

    public <T> Mono<T> sendPostRequestInternal(String uri, Object bodyValue, Class<T> responseType) {
        return internalWebClientBuilder.build().post()
                .uri(uri)
                .bodyValue(bodyValue)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client error occurred while requesting " + uri))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server error occurred while requesting " + uri))
                )
                .bodyToMono(responseType)
                .doOnError(WebClientResponseException.class, e -> logger.error("Error response: {}", e.getResponseBodyAsString()))
                .onErrorResume(e -> {
                    logger.error("Error occurred while requesting " + uri, e);
                    throw new RuntimeException("Failed to retrieve data from " + uri, e);
                });
    }

}