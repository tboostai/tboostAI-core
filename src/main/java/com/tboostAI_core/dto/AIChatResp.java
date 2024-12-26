package com.tboostAI_core.dto;

import com.tboostAI_core.entity.request_entity.SearchVehicleListRequest;
import lombok.Data;

@Data
public class AIChatResp {
    private String content;
    private boolean userContentSufficient;
    private boolean systemAccurateEnough;
    private String systemAccurateRate;
    private SearchVehicleListRequest requestParams;
}
