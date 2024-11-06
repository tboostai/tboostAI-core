package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "Vehicle_feature")
public class VehicleFeatureEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "VehicleFeatureEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
