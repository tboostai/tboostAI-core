package com.tboostintelli_core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Table(name = "Vehicle_Basic_Info")
@Entity
public class VehicleBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(length = 50, nullable = false)
    private String make;

    @Column(length = 50, nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(length = 50, nullable = false)
    private String trim;

    @Column(length = 20, unique = true, nullable = false)
    private String vin;

    @Column(nullable = false)
    private int mileage;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(length = 10, nullable = false)
    private String color;

    @Column(length = 15, nullable = false)
    private String bodyType;

    @Column(length = 10, nullable = false)
    private String engineType;

    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal engineSize;

    @Column(nullable = false)
    private int cylinder;

    @Column(length = 15, nullable = false)
    private String transmission;

    @Column(length = 30, nullable = false)
    private String drivetrain;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(length = 10, nullable = false)
    private String condition;

    private int capacity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicle_feature_mapping",  // 中间表名称
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<VehicleFeature> features;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleImage> images;

    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private Timestamp listingDate;

    @Column(nullable = false)
    private int sourceId;
}
