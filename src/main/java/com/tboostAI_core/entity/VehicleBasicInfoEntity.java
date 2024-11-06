package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "Vehicle_Basic_Info")
@Entity
public class VehicleBasicInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "make", length = 50)
    private String make;

    @Column(name = "model", length = 50)
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "trim", length = 50)
    private String trim;

    @Column(name = "vin", length = 20)
    private String vin;

    @Column(name = "mileage")
    private int mileage;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<VehiclePriceEntity> price;

    @Column(name = "exterior_color", length = 50)
    private String exteriorColor;

    @Column(name = "interior_color", length = 50)
    private String interiorColor;

    @Column(name = "body_type", length = 30)
    private String bodyType;

    @Column(name = "engine_type", length = 30)
    private String engineType;

    @Column(name = "engine_size", precision = 3, scale = 1)
    private BigDecimal engineSize;

    @Column(name = "cylinder")
    private int cylinder;

    @Column(name = "transmission", length = 30)
    private String transmission;

    @Column(name = "drivetrain", length = 50)
    private String drivetrain;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity locationEntity;

    @Column(name = "vehicle_condition", length = 30)
    private String vehicleCondition;

    @Column(name = "engine_info", length = 50)
    private String engineInfo;

    @Column(name = "cylinder_info", length = 50)
    private String cylinderInfo;

    @Column(name = "warranty", length = 200)
    private String warranty;

    @Column(name = "vehicle_title", length = 100)
    private String vehicleTitle;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "doors")
    private int doors;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "vehicle_feature_mapping",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @BatchSize(size = 20)
    private List<VehicleFeatureEntity> features;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private List<VehicleImageEntity> images;

    @Column(name = "listing_date")
    private Timestamp listingDate;

    @Column(name = "source_id")
    private int sourceId;

    // 一个车对应一个卖家，卖家可以有多个车
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;

    // 一个车可以有多个支付信息
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PaymentInfoEntity> paymentInfos;

    // 一个车可以有多个税务信息
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TaxEntity> taxInfos;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ai_description", columnDefinition = "TEXT")
    private String aiDescription;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private AvailabilityEntity availability;

    @Override
    public String toString() {
        return "VehicleBasicInfoEntity{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", trim='" + trim + '\'' +
                ", vin='" + vin + '\'' +
                ", mileage=" + mileage +
                ", exteriorColor='" + exteriorColor + '\'' +
                ", interiorColor='" + interiorColor + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", engineType='" + engineType + '\'' +
                ", engineSize=" + engineSize +
                ", cylinder=" + cylinder +
                ", transmission='" + transmission + '\'' +
                ", drivetrain='" + drivetrain + '\'' +
                ", vehicleCondition='" + vehicleCondition + '\'' +
                ", engineInfo='" + engineInfo + '\'' +
                ", cylinderInfo='" + cylinderInfo + '\'' +
                ", warranty='" + warranty + '\'' +
                ", vehicleTitle='" + vehicleTitle + '\'' +
                ", capacity=" + capacity +
                ", doors=" + doors +
                ", listingDate=" + listingDate +
                ", sourceId=" + sourceId +
                ", seller=" + seller +
                ", description='" + description + '\'' +
                ", aiDescription='" + aiDescription + '\'' +
                '}';
    }
}
