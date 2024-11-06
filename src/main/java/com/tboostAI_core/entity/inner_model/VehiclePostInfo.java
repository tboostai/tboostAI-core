package com.tboostAI_core.entity.inner_model;

import lombok.Data;

import java.util.List;

@Data
public class VehiclePostInfo {
    private String title;
    private String subtitle;
    private String itemCreationDate;
    private String itemEndDate;
    private List<String> buyingOptions;
    private String itemAffiliateWebUrl;
    private String itemWebUrl;
    private Seller seller;
}
