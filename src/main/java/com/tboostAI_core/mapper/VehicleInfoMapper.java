package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.entity.VehicleBasicInfoEntity;
import com.tboostAI_core.entity.VehicleFeatureEntity;
import com.tboostAI_core.entity.inner_model.VehicleBasicInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        uses = {
                LocationMapper.class
        }
)
public interface VehicleInfoMapper {
        VehicleInfoMapper INSTANCE = Mappers.getMapper(VehicleInfoMapper.class);

        @Mapping(target = "aiDescription", expression = "java(com.tboostAI_core.utils.CommonUtils.convertStringToList(entity.getAiDescription()))")
        @Mapping(target = "features", expression = "java(toFeatureList(entity.getFeatures()))")
        @Mapping(target = "location", source = "locationEntity")
        VehicleBasicInfo toVehicleBasicInfo(VehicleBasicInfoEntity entity);

        VehicleBasicInfoDTO toVehicleBasicInfoDTO(VehicleBasicInfo vehicleBasicInfo);

        default List<String> toFeatureList(List<VehicleFeatureEntity> featureEntities) {
                return featureEntities.stream().map(VehicleFeatureEntity::getName).toList();
        }
}


