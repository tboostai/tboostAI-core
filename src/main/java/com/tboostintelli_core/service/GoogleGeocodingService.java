package com.tboostintelli_core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import com.tboostintelli_core.config.GoogleApiConfigProperties;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

import static com.tboostintelli_core.common.GeneralConstants.*;

@Service
public class GoogleGeocodingService {

    @Resource
    private WebClient webClient;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private GoogleApiConfigProperties googleApiConfigProperties;

    public Mono<LatLng> getLatLngFromAddress(String address) {

        URI googleUri  = UriComponentsBuilder.newInstance()
                .scheme(HTTPS)
                .host(googleApiConfigProperties.getBaseUrl())
                .queryParam(GOOGLE_MAP_API_ADDR, address)
                .queryParam(GOOGLE_MAP_API_KEY, googleApiConfigProperties.getKey())
                .build()
                .toUri();

        Mono<String> responseStr = webClient.get()
                .uri(googleUri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client error occurred while requesting Google Map Geocoding API."))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server error occurred while requesting Google Map Geocoding API."))
                )
                .bodyToMono(String.class);

        return responseStr.doOnError(WebClientResponseException.class, e -> {
                    System.err.println("Error response: " + e.getResponseBodyAsString());
                })
                .onErrorResume(e -> {
                    throw new RuntimeException("Failed to retrieve geocode data", e);
                }).map(this::extractLatLng);
    }

    private LatLng extractLatLng(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode locationNode = root.path("results").get(0).path("geometry").path("location");
            double lat = locationNode.path("lat").asDouble();
            double lng = locationNode.path("lng").asDouble();
            return new LatLng(lat, lng);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }
}

