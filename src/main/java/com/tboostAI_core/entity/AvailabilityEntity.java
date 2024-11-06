package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "availability")
public class AvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "delivery_options")
    private String deliveryOptions;

    @Column(name = "estimated_availability_status", length = 100)
    private String estimatedAvailabilityStatus;

    @Column(name = "estimated_available_quantity")
    private Integer estimatedAvailableQuantity;

    @Column(name = "estimated_sold_quantity")
    private Integer estimatedSoldQuantity;

    @Column(name = "estimated_remaining_quantity")
    private Integer estimatedRemainingQuantity;

    @OneToOne(mappedBy = "availability", cascade = CascadeType.ALL)
    private VehicleBasicInfoEntity vehicleBasicInfo;

    @Override
    public String toString() {
        return "AvailabilityEntity{" +
                "deliveryOptions='" + deliveryOptions + '\'' +
                ", estimatedAvailabilityStatus='" + estimatedAvailabilityStatus + '\'' +
                ", estimatedAvailableQuantity=" + estimatedAvailableQuantity +
                ", estimatedSoldQuantity=" + estimatedSoldQuantity +
                ", estimatedRemainingQuantity=" + estimatedRemainingQuantity +
                '}';
    }
}
