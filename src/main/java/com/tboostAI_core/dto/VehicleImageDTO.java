package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "VehicleImageDTO", description = "DTO representing an image of a vehicle, including its URL and dimensions")
public class VehicleImageDTO {

    @Schema(description = "The URL of the vehicle image", example = "https://example.com/images/vehicle1.jpg")
    private String url;

    @Schema(description = "The width of the vehicle image in pixels", example = "1024")
    private double width;

    @Schema(description = "The height of the vehicle image in pixels", example = "768")
    private double height;
}
