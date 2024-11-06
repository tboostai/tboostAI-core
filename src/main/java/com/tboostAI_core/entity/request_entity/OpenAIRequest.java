package com.tboostAI_core.entity.request_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class OpenAIRequest implements Serializable {

    @JsonProperty("messages")
    private List<Message> messages;

}