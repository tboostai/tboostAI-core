package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.SellerDTO;
import com.tboostAI_core.entity.SellerEntity;
import com.tboostAI_core.entity.inner_model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EbaySellerMapper {
    EbaySellerMapper INSTANCE = Mappers.getMapper(EbaySellerMapper.class);

    Seller toSeller(SellerEntity entity);
    SellerDTO toSellerDTO(Seller seller);
}
