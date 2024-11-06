package com.tboostAI_core.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInfoDTO {
    private String paymentMethodType;
    private List<String> paymentInstructions;
}
