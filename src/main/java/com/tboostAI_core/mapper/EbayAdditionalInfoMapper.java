package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;
import com.tboostAI_core.entity.EbayAdditionalInfoEntity;
import com.tboostAI_core.entity.inner_model.EbayAdditionalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EbayAdditionalInfoMapper {

    EbayAdditionalInfoMapper INSTANCE = Mappers.getMapper(EbayAdditionalInfoMapper.class);

    EbayAdditionalInfo toEbayAdditionalInfo(EbayAdditionalInfoEntity entity);

    EbayAdditionalInfoDTO toEbayAdditionalInfoDTO(EbayAdditionalInfo ebayAdditionalInfo);
}
