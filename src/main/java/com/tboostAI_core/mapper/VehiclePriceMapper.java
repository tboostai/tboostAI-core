package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.VehiclePriceDTO;
import com.tboostAI_core.entity.VehiclePriceEntity;
import com.tboostAI_core.entity.inner_model.VehiclePrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface VehiclePriceMapper {
    VehiclePriceMapper INSTANCE = Mappers.getMapper(VehiclePriceMapper.class);

    VehiclePrice toVehiclePrice(VehiclePriceEntity entity);

    List<VehiclePrice> toVehiclePriceList(List<VehiclePriceEntity> entities);

    VehiclePriceDTO toVehiclePriceDTO(VehiclePrice vehiclePrice);

    List<VehiclePriceDTO> toVehiclePriceDTOList(List<VehiclePrice> vehiclePrices);
}
