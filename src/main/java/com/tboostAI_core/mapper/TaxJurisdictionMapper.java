package com.tboostAI_core.mapper;

import com.tboostAI_core.entity.TaxEntity;
import com.tboostAI_core.entity.inner_model.TaxJurisdiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = RegionMapper.class)
public interface TaxJurisdictionMapper {

    @Mapping(target = "region", ignore = true)
    TaxJurisdiction toTaxJurisdiction(TaxEntity entity);
}
