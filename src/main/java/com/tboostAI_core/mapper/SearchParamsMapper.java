package com.tboostAI_core.mapper;

import com.tboostAI_core.entity.request_entity.SearchVehicleListRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchParamsMapper {

    SearchParamsMapper INSTANCE = Mappers.getMapper(SearchParamsMapper.class);

    SearchVehicleListRequest mapWithDefaultValues(SearchVehicleListRequest request);
}

