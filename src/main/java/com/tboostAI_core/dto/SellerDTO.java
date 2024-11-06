package com.tboostAI_core.dto;

import lombok.Data;

@Data
public class SellerDTO {
    private String username;
    private String platform;
    private String feedbackPercentage;
    private Integer feedbackScore;
}
