package com.tboostAI_core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class VehicleInfoSummaryDTO {

    private String make;
    private String model;
    private Integer year;
    private String trim;
    private Integer mileage;
    private BigDecimal price;
    private String color;
    private String drivetrain;
    private String city;
    private String stateProvince;
    private String country;
    private String condition;
    private Integer capacity;
    private String description;
    private Timestamp listingDate;
}
