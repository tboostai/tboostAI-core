package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "payment_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;  // 自增主键

    @Column(name = "payment_method_type", length = 50)
    private String paymentMethodType;

    @Column(name = "payment_instructions")
    private String paymentInstructions;

    // 一个 PaymentInfo 对应一个 Vehicle
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleBasicInfoEntity vehicle;

    @Override
    public String toString() {
        return "PaymentInfoEntity{" +
                "paymentMethodType='" + paymentMethodType + '\'' +
                ", paymentInstructions='" + paymentInstructions + '\'' +
                '}';
    }
}
