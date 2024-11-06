package com.tboostAI_core.entity.inner_model;

import lombok.Data;

@Data
public class Tax {
    private TaxJurisdiction taxJurisdiction;
    private String taxType;
    private Boolean shippingAndHandlingTaxed;
    private Boolean includedInPrice;
    private Boolean ebayCollectAndRemitTax;
}
