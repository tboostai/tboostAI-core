package com.tboostAI_core.dto;

import com.tboostAI_core.entity.inner_model.Seller;

import java.util.List;

public class PostDTO {
    private String title;
    private String subtitle;
    private String itemCreationDate;
    private String itemEndDate;
    private List<String> buyingOptions;
    private String itemAffiliateWebUrl;
    private String itemWebUrl;
    private Seller seller;
}
