package com.tboostAI_core.service.impl;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;
import com.tboostAI_core.entity.EbayAdditionalInfoEntity;
import com.tboostAI_core.entity.inner_model.EbayAdditionalInfo;
import com.tboostAI_core.mapper.EbayAdditionalInfoMapper;
import com.tboostAI_core.repository.EbayAdditionalInfoRepo;
import com.tboostAI_core.service.EbayAdditionalInfoService;
import org.springframework.stereotype.Service;

@Service
public class EbayAdditionalInfoServiceImpl implements EbayAdditionalInfoService {

    private final EbayAdditionalInfoRepo ebayAdditionalInfoRepo;
    public EbayAdditionalInfoServiceImpl(EbayAdditionalInfoRepo ebayAdditionalInfoRepo) {
        this.ebayAdditionalInfoRepo = ebayAdditionalInfoRepo;
    }

    public EbayAdditionalInfoDTO getEbayAdditionalInfoByVehicleId(Long vehicleId) {

        EbayAdditionalInfoEntity ebayAdditionalInfoEntity = ebayAdditionalInfoRepo.findEbayAdditionalInfoByVehicleUuid(vehicleId);

        EbayAdditionalInfo ebayAdditionalInfo = EbayAdditionalInfoMapper.INSTANCE.toEbayAdditionalInfo(ebayAdditionalInfoEntity);

        return EbayAdditionalInfoMapper.INSTANCE.toEbayAdditionalInfoDTO(ebayAdditionalInfo);
    }
}
