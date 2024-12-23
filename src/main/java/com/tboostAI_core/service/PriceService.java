package com.tboostAI_core.service;

import com.tboostAI_core.dto.VehiclePriceDTO;

import java.util.List;

public interface PriceService {
    List<VehiclePriceDTO> findAllPricesByVehicleId(Long vehicleId);
}
