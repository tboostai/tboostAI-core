package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.LocationDTO;
import com.tboostAI_core.entity.LocationEntity;
import com.tboostAI_core.entity.inner_model.Location;
import org.mapstruct.Mapper;


@Mapper
public interface LocationMapper {
    Location toLocation(LocationEntity entity);

    LocationDTO toLocationDTO(Location location);
}
