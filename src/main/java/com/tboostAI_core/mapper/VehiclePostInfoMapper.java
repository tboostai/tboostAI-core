package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.PostDTO;
import com.tboostAI_core.entity.PostEntity;
import com.tboostAI_core.entity.inner_model.VehiclePostInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        uses = {
                EbaySellerMapper.class,
                DateMapper.class
        }
)
public interface VehiclePostInfoMapper {

        VehiclePostInfoMapper INSTANCE = Mappers.getMapper(VehiclePostInfoMapper.class);

        @Mapping(target = "buyingOptions", expression = "java(com.tboostAI_core.utils.CommonUtils.convertStringToList(entity.getBuyingOptions()))")
        VehiclePostInfo toVehiclePostInfo(PostEntity entity);

        PostDTO toPostDTO(VehiclePostInfo vehiclePostInfo);
}
