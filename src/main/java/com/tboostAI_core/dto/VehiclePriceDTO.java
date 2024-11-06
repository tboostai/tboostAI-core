package com.tboostAI_core.dto;

import com.tboostAI_core.entity.VehicleBasicInfoEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehiclePriceDTO {
    private Long id;
    private VehicleBasicInfoEntity vehicle;
    private BigDecimal price; // 当前价格，带两位小数
    private String currency; // 当前货币单位, 例如 USD, CAD
    private BigDecimal convertedFromValue; // 原始货币价格（可选）
    private String convertedFromCurrency; // 原始货币单位（可选）
}
