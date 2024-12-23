package com.tboostAI_core.service;

import com.tboostAI_core.dto.SearchVehiclesResponse;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface VehicleBasicInfoService {


    SearchVehiclesResponse searchVehicles(List<String> make, List<String> model,
                                          Integer minYear, Integer maxYear,
                                          List<String> trim, Double mileage,
                                          Double minPrice, Double maxPrice,
                                          List<String> color, List<String> bodyType,
                                          List<String> engineType, List<String> transmission,
                                          List<String> drivetrain, String address, List<String> condition,
                                          Integer capacity, List<String> features, Double distance, Pageable pageable);

    SearchVehiclesResponse searchVehiclesByLLM(String sessionId, Double minPrice, Double maxPrice,
                                               List<String> bodyType, List<String> engineType,
                                               String content, String address, int distance, Pageable pageable);
}
