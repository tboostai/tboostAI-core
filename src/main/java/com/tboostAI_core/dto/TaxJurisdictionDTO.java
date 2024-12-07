package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TaxJurisdictionDTO", description = "DTO representing the tax jurisdiction details, including region and jurisdiction ID")
public class TaxJurisdictionDTO {

    @Schema(description = "The region associated with the tax jurisdiction", implementation = RegionDTO.class)
    private RegionDTO region;

    @Schema(description = "The unique identifier for the tax jurisdiction", example = "US-CA")
    private String taxJurisdictionId;
}
