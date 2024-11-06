package com.tboostAI_core.entity.inner_model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {

    private String country;
    private String stateProvince;
    private String city;
    private String street;
    private String postalCode;
    private String unit;
    private double latitude;
    private double longitude;
}
