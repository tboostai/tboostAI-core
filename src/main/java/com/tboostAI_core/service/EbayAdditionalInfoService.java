package com.tboostAI_core.service;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;

public interface EbayAdditionalInfoService {
    EbayAdditionalInfoDTO getEbayAdditionalInfoByVehicleId(Long vehicleId);
}
