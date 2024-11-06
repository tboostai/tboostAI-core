package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.TaxJurisdictionDTO;
import com.tboostAI_core.entity.TaxEntity;
import com.tboostAI_core.entity.inner_model.TaxJurisdiction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = RegionMapper.class)
public interface TaxJurisdictionMapper {

    @Mapping(target = "region.regionName", source = "regionName")
    @Mapping(target = "region.regionType", source = "regionType")
    @Mapping(target = "region.regionId", source = "regionId")
    TaxJurisdiction toTaxJurisdiction(TaxEntity entity);
    List<TaxJurisdiction> toTaxJurisdictionList(List<TaxEntity> entities);

    TaxJurisdictionDTO toTaxJurisdictionDTO(TaxJurisdiction entity);
    List<TaxJurisdictionDTO> toTaxJurisdictionDtoList(List<TaxJurisdiction> taxJurisdictions);
}
