package com.tboostAI_core.entity.inner_model;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInfo {
    private String paymentMethodType;
    private List<String> paymentInstructions;
}
