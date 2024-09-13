package com.tboostAI_core.mapper;

import com.tboostAI_core.entity.request_entity.SearchVehicleListRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchParamsMapper {

    SearchParamsMapper INSTANCE = Mappers.getMapper(SearchParamsMapper.class);

    @Mapping(target = "make", source = "make", defaultValue = "ALL")
    @Mapping(target = "model", source = "model", defaultValue = "ALL")
    @Mapping(target = "trim", source = "trim", defaultValue = "ALL")
    @Mapping(target = "color", source = "color", defaultValue = "ALL")
    @Mapping(target = "bodyType", source = "bodyType", defaultValue = "ALL")
    @Mapping(target = "engineType", source = "engineType", defaultValue = "ALL")
    @Mapping(target = "transmission", source = "transmission", defaultValue = "ALL")
    @Mapping(target = "drivetrain", source = "drivetrain", defaultValue = "ALL")
    @Mapping(target = "condition", source = "condition", defaultValue = "ALL")
    SearchVehicleListRequest mapWithDefaultValues(SearchVehicleListRequest request);
}

