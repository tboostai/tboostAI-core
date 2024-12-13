package com.tboostAI_core.dto;

import com.tboostAI_core.common.SearchTypeEnum;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class SearchVehiclesResponse {
    private Page<VehicleBasicInfoDTO> vehicles;
    private SearchTypeEnum searchType;
}
