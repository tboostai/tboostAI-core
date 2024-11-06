package com.tboostAI_core.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "title", columnDefinition = "text", length = 100)
    private String title;

    @Column(name = "subtitle", columnDefinition = "text", length = 100)
    private String subtitle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "item_creation_date")
    private Date itemCreationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "item_end_date")
    private Date itemEndDate;

    @Column(name = "buying_options")
    private String buyingOptions;

    @Column(name = "item_affiliate_web_url", columnDefinition = "text")
    private String itemAffiliateWebUrl;

    @Column(name = "item_web_url", columnDefinition = "text")
    private String itemWebUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private VehicleBasicInfoEntity vehicle;

    @Override
    public String toString() {
        return "PostEntity{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", itemCreationDate=" + itemCreationDate +
                ", itemEndDate=" + itemEndDate +
                ", buyingOptions='" + buyingOptions + '\'' +
                ", itemAffiliateWebUrl='" + itemAffiliateWebUrl + '\'' +
                ", itemWebUrl='" + itemWebUrl + '\'' +
                '}';
    }
}
