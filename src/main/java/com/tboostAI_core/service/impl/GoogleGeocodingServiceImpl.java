package com.tboostAI_core.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import com.tboostAI_core.config.GoogleApiConfigProperties;
import com.tboostAI_core.service.GoogleGeocodingService;
import com.tboostAI_core.utils.WebClientUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.tboostAI_core.common.GeneralConstants.*;

@Service
public class GoogleGeocodingServiceImpl implements GoogleGeocodingService {

    @Resource                                                  
    private ObjectMapper objectMapper;
    @Resource
    private GoogleApiConfigProperties googleApiConfigProperties;
    @Resource
    private WebClientUtils webClientUtils;

    public Mono<LatLng> getLatLngFromAddress(String address) {

        URI googleUri = UriComponentsBuilder.newInstance()
                .scheme(HTTPS)
                .host(googleApiConfigProperties.getBaseUrl())
                .queryParam(GOOGLE_MAP_API_ADDR, address)
                .queryParam(GOOGLE_MAP_API_KEY, googleApiConfigProperties.getKey())
                .build(false)
                .toUri();

        Mono<String> googleResponseStr = webClientUtils.sendGetRequestExternal(googleUri.toString(), String.class);

        return googleResponseStr.map(this::extractLatLng);
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

