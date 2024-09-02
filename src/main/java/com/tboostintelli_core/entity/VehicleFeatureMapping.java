package com.tboostintelli_core.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_feature_mapping")
public class VehicleFeatureMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(name = "feature_id")
    private Long featureId;
}