package com.tboostAI_core.dto;

import lombok.Data;

@Data
public class TaxDTO {
    private TaxJurisdictionDTO taxJurisdictionDto;
    private String taxType;
    private Boolean shippingAndHandlingTaxed;
    private Boolean includedInPrice;
    private Boolean ebayCollectAndRemitTax;
}
