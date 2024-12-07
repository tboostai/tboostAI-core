package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "AvailabilityDTO", description = "DTO representing the availability details of a product")
public class AvailabilityDTO {

    @Schema(description = "List of delivery options available", example = "[\"Standard Shipping\", \"Express Shipping\"]")
    private List<String> deliveryOptions;

    @Schema(description = "Estimated availability status of the product", example = "In Stock")
    private String estimatedAvailabilityStatus;

    @Schema(description = "Estimated available quantity of the product", example = "100")
    private Integer estimatedAvailableQuantity;

    @Schema(description = "Estimated quantity of the product sold", example = "50")
    private Integer estimatedSoldQuantity;

    @Schema(description = "Estimated remaining quantity of the product", example = "50")
    private Integer estimatedRemainingQuantity;
}

