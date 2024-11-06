package com.tboostAI_core.entity.inner_model;

import lombok.Data;

import java.util.List;

@Data
public class Availability {
    private List<String> deliveryOptions;
    private String estimatedAvailabilityStatus;
    private Integer estimatedAvailableQuantity;
    private Integer estimatedSoldQuantity;
    private Integer estimatedRemainingQuantity;
}
