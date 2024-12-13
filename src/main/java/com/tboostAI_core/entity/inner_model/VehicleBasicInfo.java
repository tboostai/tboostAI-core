package com.tboostAI_core.entity.inner_model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class VehicleBasicInfo {

    private long uuid;
    private String make;
    private String model;
    private int year;
    private String trim;
    private String vin;
    private int mileage;
    private String exteriorColor;
    private String interiorColor;
    private String bodyType;
    private String engineType;
    private BigDecimal engineSize;
    private int cylinder;
    private String transmission;
    private String drivetrain;
    private String vehicleCondition;
    private String description;
    private List<String> aiDescription;
    private int capacity;
    private Timestamp listingDate;
    private int sourceId;
    private String engineInfo;
    private String cylinderInfo;
    private String warranty;
    private String vehicleTitle;
    private int doors;
    private List<VehicleImage> images;
    private Location location;
    private List<String> features;
}
