package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.VehicleImageDTO;
import com.tboostAI_core.entity.VehicleImageEntity;
import com.tboostAI_core.entity.inner_model.VehicleImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface VehicleImageMapper {
    VehicleImage toVehicleImage(VehicleImageEntity entity);

    VehicleImageDTO toVehicleImageDTO(VehicleImage vehicleImage);

    List<VehicleImageDTO> toVehicleImageDTOList(List<VehicleImage> vehicleImages);
}
