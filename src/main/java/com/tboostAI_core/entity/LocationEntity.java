package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "location")
public class LocationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "state_province", length = 100)
    private String stateProvince;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "street", length = 150)
    private String street;

    @Column(name = "postal_code", length = 15)
    private String postalCode;

    @Column(name = "unit", length = 10)
    private String unit;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "locationEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VehicleBasicInfoEntity> vehicles;

    @Override
    public String toString() {
        return "LocationEntity{" +
                "country='" + country + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", unit='" + unit + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
