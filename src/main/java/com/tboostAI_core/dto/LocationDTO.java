package com.tboostAI_core.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private String country;
    private String stateProvince;
    private String city;
    private String street;
    private String postalCode;
    private String unit;
    private double latitude;
    private double longitude;
}
