package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SellerDTO", description = "DTO representing seller information, including platform and feedback details")
public class SellerDTO {

    @Schema(description = "The username of the seller", example = "JohnDoe123")
    private String username;

    @Schema(description = "The platform where the seller is registered", example = "eBay")
    private String platform;

    @Schema(description = "The seller's feedback percentage, represented as a string", example = "99.5%")
    private String feedbackPercentage;

    @Schema(description = "The seller's feedback score, represented as an integer", example = "1200")
    private Integer feedbackScore;
}
