package com.tboostAI_core.repository;

import com.tboostAI_core.entity.PaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentInfoRepo extends JpaRepository<PaymentInfoEntity, Long> {
    List<PaymentInfoEntity> findByVehicleUuid(Long vehicleId);
}
