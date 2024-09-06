package com.tboostAI_core.dto;

import com.tboostAI_core.entity.Location;
import com.tboostAI_core.entity.VehicleImage;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class VehicleBasicInfoDTO {
        private Long uuid;
        private String make;
        private String model;
        private int year;
        private String trim;
        private String vin;
        private int mileage;
        private BigDecimal price;
        private String color;
        private String bodyType;
        private String engineType;
        private String transmission;
        private String drivetrain;
        private Location location;
        private String condition;
        private int capacity;
        private List<String> features;
        private List<VehicleImage> images;
        private String description;
        private Timestamp listingDate;
        private int sourceId;
}
