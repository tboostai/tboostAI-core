package com.tboostAI_core.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class VehicleBasicInfoDTO {
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
        private LocationDTO location;
        private String vehicleCondition;
        private String engineInfo;
        private String cylinderInfo;
        private String warranty;
        private String vehicleTitle;
        private int capacity;
        private int doors;
        private List<String> features;
        private List<VehicleImageDTO> images;
        private Timestamp listingDate;
        private int sourceId;
        private String description;
        private List<String> aiDescription;
}
