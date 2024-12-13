package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "VehiclePriceDTO", description = "DTO representing the pricing details of a vehicle, including currency and converted values")
public class VehiclePriceDTO {

    @Schema(description = "The unique identifier for the vehicle price entry", example = "101")
    private Long id;

    @Schema(description = "The vehicle information associated with this price", implementation = VehicleBasicInfoDTO.class)
    private VehicleBasicInfoDTO vehicle;

    @Schema(description = "The price of the vehicle in the specified currency", example = "25000.00")
    private BigDecimal price;

    @Schema(description = "The currency of the price", example = "USD")
    private String currency;

    @Schema(description = "The price value converted from a different currency", example = "20000.00")
    private BigDecimal convertedFromValue;

    @Schema(description = "The original currency of the converted price", example = "EUR")
    private String convertedFromCurrency;
}
