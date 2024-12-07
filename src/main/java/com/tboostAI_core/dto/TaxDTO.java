package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TaxDTO", description = "DTO representing tax details, including jurisdiction, type, and additional tax-related flags")
public class TaxDTO {

    @Schema(description = "Details about the tax jurisdiction", implementation = TaxJurisdictionDTO.class)
    private TaxJurisdictionDTO taxJurisdictionDto;

    @Schema(description = "The type of tax applied", example = "Sales Tax")
    private String taxType;

    @Schema(description = "Indicates if shipping and handling are taxed", example = "true")
    private Boolean shippingAndHandlingTaxed;

    @Schema(description = "Indicates if the tax is included in the item's price", example = "false")
    private Boolean includedInPrice;

    @Schema(description = "Indicates if eBay collects and remits the tax", example = "true")
    private Boolean ebayCollectAndRemitTax;
}
