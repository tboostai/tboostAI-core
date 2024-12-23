package com.tboostAI_core.service;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;

public interface EbayAdditionalInfoService {
    public EbayAdditionalInfoDTO getEbayAdditionalInfoByVehicleId(Long vehicleId);
}
