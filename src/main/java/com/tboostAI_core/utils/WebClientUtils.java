package com.tboostAI_core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtils {

    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(WebClientUtils.class);

    public WebClientUtils(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public <T> Mono<T> sendGetRequest(String uri, Class<T> responseType) {
        return webClientBuilder.build().get()
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

}