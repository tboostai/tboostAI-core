package com.tboostAI_core.repository;

import com.tboostAI_core.entity.EbayAdditionalInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbayAdditionalInfoRepo extends JpaRepository<EbayAdditionalInfoEntity, Long> {

    EbayAdditionalInfoEntity findEbayAdditionalInfoByVehicleUuid(Long VehicleId);
}
