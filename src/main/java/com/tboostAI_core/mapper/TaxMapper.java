package com.tboostAI_core.mapper;

import org.mapstruct.Mapper;

@Mapper(
        uses = {
                TaxJurisdictionMapper.class,
                RegionMapper.class
        }
)
public interface TaxMapper {
}
