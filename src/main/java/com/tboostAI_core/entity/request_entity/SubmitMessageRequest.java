package com.tboostAI_core.entity.request_entity;

import lombok.Data;

@Data
public class SubmitMessageRequest {
    private String sessionId;
    private String message;
}
