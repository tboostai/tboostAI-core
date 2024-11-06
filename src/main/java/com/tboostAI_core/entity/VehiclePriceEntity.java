package com.tboostAI_core.entity;

import com.tboostAI_core.common.VehiclePriceEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "price")
@Data
public class VehiclePriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleBasicInfoEntity vehicle;

    @Column(name = "price_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal price; // 当前价格，带两位小数

    @Column(name = "price_currency", nullable = false, length = 3)
    private String currency; // 当前货币单位, 例如 USD, CAD

    @Column(name = "converted_from_value", precision = 15, scale = 2)
    private BigDecimal convertedFromValue; // 原始货币价格（可选）

    @Column(name = "converted_from_currency", length = 3)
    private String convertedFromCurrency; // 原始货币单位（可选）

    @Column(name = "price_type")
    @Enumerated(EnumType.ORDINAL)
    private VehiclePriceEnum priceType;

    @Override
    public String toString() {
        return "VehiclePriceEntity{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", convertedFromValue=" + convertedFromValue +
                ", convertedFromCurrency='" + convertedFromCurrency + '\'' +
                '}';
    }
}
