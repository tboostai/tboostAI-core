package com.tboostAI_core.service;

import com.google.maps.model.LatLng;
import reactor.core.publisher.Mono;

public interface GoogleGeocodingService {
    Mono<LatLng> getLatLngFromAddress(String address);
}
