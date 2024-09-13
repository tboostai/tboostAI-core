package com.tboostAI_core.entity.request_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OpenAIRequest {

    @JsonProperty("messages")
    private List<Message> messages;

}