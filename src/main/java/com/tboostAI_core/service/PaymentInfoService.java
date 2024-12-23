package com.tboostAI_core.service;

import com.tboostAI_core.dto.PaymentInfoDTO;

import java.util.List;

public interface PaymentInfoService {
    List<PaymentInfoDTO> getPaymentInfosByVehicleId(Long vehicleId);
}
