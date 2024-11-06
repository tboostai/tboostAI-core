package com.tboostAI_core.service;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;
import com.tboostAI_core.entity.EbayAdditionalInfoEntity;
import com.tboostAI_core.entity.inner_model.EbayAdditionalInfo;
import com.tboostAI_core.mapper.EbayAdditionalInfoMapper;
import com.tboostAI_core.repository.EbayAdditionalInfoRepo;
import org.springframework.stereotype.Service;

@Service
public class EbayAdditionalInfoService {

    private final EbayAdditionalInfoRepo ebayAdditionalInfoRepo;
    public EbayAdditionalInfoService(EbayAdditionalInfoRepo ebayAdditionalInfoRepo) {
        this.ebayAdditionalInfoRepo = ebayAdditionalInfoRepo;
    }

    public EbayAdditionalInfoDTO getEbayAdditionalInfoByVehicleId(Long vehicleId) {

        EbayAdditionalInfoEntity ebayAdditionalInfoEntity = ebayAdditionalInfoRepo.findEbayAdditionalInfoByVehicleUuid(vehicleId);

        EbayAdditionalInfo ebayAdditionalInfo = EbayAdditionalInfoMapper.INSTANCE.toEbayAdditionalInfo(ebayAdditionalInfoEntity);

        return EbayAdditionalInfoMapper.INSTANCE.toEbayAdditionalInfoDTO(ebayAdditionalInfo);
    }
}
