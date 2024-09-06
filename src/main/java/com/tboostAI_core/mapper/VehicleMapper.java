package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.VehicleBasicInfoDTO;
import com.tboostAI_core.entity.VehicleBasicInfo;
import com.tboostAI_core.mapper.impl.FeatureMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = FeatureMapper.class)

public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    @Mapping(source = "features", target = "features", qualifiedBy = FeatureToStringMapping.class)
    VehicleBasicInfoDTO vehicleBasicInfoToVehicleBasicInfoDTO(VehicleBasicInfo vehicleBasicInfo);
}