package com.tboostAI_core.dto;

import lombok.Data;

import java.util.List;

@Data
public class AvailabilityDTO {
    private List<String> deliveryOptions;
    private String estimatedAvailabilityStatus;
    private Integer estimatedAvailableQuantity;
    private Integer estimatedSoldQuantity;
    private Integer estimatedRemainingQuantity;
}
