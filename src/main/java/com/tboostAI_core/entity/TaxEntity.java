package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tax_info")
public class TaxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "tax_jurisdiction_id", length = 10)
    private String taxJurisdictionId;

    @Column(name = "tax_type", length = 30)
    private String taxType;

    @Column(name = "shipping_and_handling_taxed")
    private Boolean shippingAndHandlingTaxed;

    @Column(name = "included_in_price")
    private Boolean includedInPrice;

    @Column(name = "ebay_collect_and_remit_tax")
    private Boolean ebayCollectAndRemitTax;

    @Column(name = "region_name", length = 100)
    private String regionName;

    @Column(name = "region_type", length = 15)
    private String regionType;

    @Column(name = "region_id", length = 10)
    private String regionId;

    // 一个 TaxEntity 对应一个 Vehicle
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleBasicInfoEntity vehicle;

    @Override
    public String toString() {
        return "TaxEntity{" +
                "taxJurisdictionId='" + taxJurisdictionId + '\'' +
                ", taxType='" + taxType + '\'' +
                ", shippingAndHandlingTaxed=" + shippingAndHandlingTaxed +
                ", includedInPrice=" + includedInPrice +
                ", ebayCollectAndRemitTax=" + ebayCollectAndRemitTax +
                ", regionName='" + regionName + '\'' +
                ", regionType='" + regionType + '\'' +
                ", regionId='" + regionId + '\'' +
                '}';
    }
}
