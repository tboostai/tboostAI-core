package com.tboostAI_core.entity.request_entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class SearchVehicleListRequest implements Serializable {
    private List<String> make;
    private List<String> model;
    private Integer minYear;
    private Integer maxYear;
    private List<String> trim;
    private Double mileage;
    private Double minPrice;
    private Double maxPrice;
    private List<String> color;
    private List<String> bodyType;
    private List<String> engineType;
    private List<String> transmission;
    private List<String> drivetrain;
    private Double longitude;
    private Double latitude;
    private List<String> condition;
    private Integer capacity;
    private List<String> features;
    private Double distance;
}
