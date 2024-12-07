package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RegionDTO", description = "DTO representing region details, including name, type, and ID")
public class RegionDTO {

    @Schema(description = "The name of the region", example = "California")
    private String regionName;

    @Schema(description = "The type of the region (e.g., state, province, or country)", example = "State")
    private String regionType;

    @Schema(description = "The unique identifier for the region", example = "US-CA")
    private String regionId;
}
