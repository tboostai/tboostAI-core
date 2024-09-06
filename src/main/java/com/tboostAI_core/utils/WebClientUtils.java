package com.tboostAI_core.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class WebClientUtils {

    private final WebClient.Builder webClientBuilder;

    public WebClientUtils(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    // 泛型方法，可以返回不同类型的响应
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
                .doOnError(WebClientResponseException.class, e -> {
                    System.err.println("Error response: " + e.getResponseBodyAsString());
                })
                .onErrorResume(e -> {
                    throw new RuntimeException("Failed to retrieve data from " + uri, e);
                });
    }

}