package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "PaymentInfoDTO", description = "DTO representing payment information, including method type and instructions")
public class PaymentInfoDTO {

    @Schema(description = "The type of payment method used", example = "Credit Card")
    private String paymentMethodType;

    @Schema(
            description = "List of instructions or notes regarding the payment",
            example = "[\"Please ensure sufficient balance on your card\", \"Keep a receipt of the payment\"]"
    )
    private List<String> paymentInstructions;
}
