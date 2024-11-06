package com.tboostAI_core.repository;

import com.tboostAI_core.entity.VehiclePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepo extends JpaRepository<VehiclePriceEntity, Long> {
    List<VehiclePriceEntity> findAllPricesByVehicleUuid(Long vehicleId);
}
