package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "LocationDTO", description = "DTO representing location details, including address and geographic coordinates")
public class LocationDTO {

    @Schema(description = "The country of the location", example = "United States")
    private String country;

    @Schema(description = "The state or province of the location", example = "California")
    private String stateProvince;

    @Schema(description = "The city of the location", example = "Los Angeles")
    private String city;

    @Schema(description = "The street address of the location", example = "123 Main St")
    private String street;

    @Schema(description = "The postal code of the location", example = "90001")
    private String postalCode;

    @Schema(description = "The unit or apartment number, if applicable", example = "Unit 5B")
    private String unit;

    @Schema(description = "The latitude coordinate of the location", example = "34.052235")
    private double latitude;

    @Schema(description = "The longitude coordinate of the location", example = "-118.243683")
    private double longitude;
}
