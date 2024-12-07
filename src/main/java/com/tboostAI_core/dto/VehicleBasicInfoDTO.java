package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Schema(name = "VehicleBasicInfoDTO", description = "DTO representing basic information about a vehicle, including specifications, condition, and location")
public class VehicleBasicInfoDTO {

        @Schema(description = "The make of the vehicle", example = "Toyota")
        private String make;

        @Schema(description = "The model of the vehicle", example = "Camry")
        private String model;

        @Schema(description = "The manufacturing year of the vehicle", example = "2020")
        private int year;

        @Schema(description = "The trim level of the vehicle", example = "LE")
        private String trim;

        @Schema(description = "The Vehicle Identification Number (VIN)", example = "1HGBH41JXMN109186")
        private String vin;

        @Schema(description = "The mileage of the vehicle in miles", example = "30000")
        private int mileage;

        @Schema(description = "The exterior color of the vehicle", example = "White")
        private String exteriorColor;

        @Schema(description = "The interior color of the vehicle", example = "Black")
        private String interiorColor;

        @Schema(description = "The body type of the vehicle", example = "Sedan")
        private String bodyType;

        @Schema(description = "The type of engine in the vehicle", example = "Gasoline")
        private String engineType;

        @Schema(description = "The size of the engine in liters", example = "2.5")
        private BigDecimal engineSize;

        @Schema(description = "The number of engine cylinders", example = "4")
        private int cylinder;

        @Schema(description = "The type of transmission in the vehicle", example = "Automatic")
        private String transmission;

        @Schema(description = "The drivetrain configuration of the vehicle", example = "FWD")
        private String drivetrain;

        @Schema(description = "The location of the vehicle", implementation = LocationDTO.class)
        private LocationDTO location;

        @Schema(description = "The condition of the vehicle", example = "Used")
        private String vehicleCondition;

        @Schema(description = "Additional engine information", example = "2.5L DOHC 16-Valve 4-Cylinder")
        private String engineInfo;

        @Schema(description = "Additional cylinder information", example = "Inline 4-cylinder")
        private String cylinderInfo;

        @Schema(description = "Details about the warranty available for the vehicle", example = "3 years or 36,000 miles")
        private String warranty;

        @Schema(description = "The title status of the vehicle", example = "Clean")
        private String vehicleTitle;

        @Schema(description = "The passenger capacity of the vehicle", example = "5")
        private int capacity;

        @Schema(description = "The number of doors on the vehicle", example = "4")
        private int doors;

        @Schema(description = "A list of features available in the vehicle", example = "[\"Air Conditioning\", \"Backup Camera\", \"Bluetooth\"]")
        private List<String> features;

        @Schema(description = "A list of images of the vehicle", implementation = VehicleImageDTO.class)
        private List<VehicleImageDTO> images;

        @Schema(description = "The date when the vehicle was listed", example = "2024-01-15T10:00:00Z")
        private Timestamp listingDate;

        @Schema(description = "The source ID of the listing", example = "12345")
        private int sourceId;

        @Schema(description = "A description of the vehicle", example = "A well-maintained, single-owner vehicle in excellent condition.")
        private String description;

        @Schema(description = "A list of AI-generated descriptions of the vehicle", example = "[\"Perfect for family trips\", \"Low mileage and great condition\"]")
        private List<String> aiDescription;
}
