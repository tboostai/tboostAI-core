package com.tboostAI_core.mapper;

import com.tboostAI_core.entity.TaxEntity;
import com.tboostAI_core.entity.inner_model.Region;
import org.mapstruct.Mapper;


@Mapper
public interface RegionMapper {
    Region toRegion(Region region);
}
