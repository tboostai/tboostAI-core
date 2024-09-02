package com.tboostintelli_core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    private String country;
    private String stateProvince;
    private String city;
    private String street;
    private String postalCode;
    private String unit;
    private String latitude;
    private String longitude;
}
