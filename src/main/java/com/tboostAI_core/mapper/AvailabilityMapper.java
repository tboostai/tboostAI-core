package com.tboostAI_core.mapper;


import com.tboostAI_core.dto.AvailabilityDTO;
import com.tboostAI_core.entity.AvailabilityEntity;
import com.tboostAI_core.entity.inner_model.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AvailabilityMapper {

   AvailabilityMapper INSTANCE = Mappers.getMapper(AvailabilityMapper.class);

   @Mapping(target = "deliveryOptions", expression = "java(com.tboostAI_core.utils.CommonUtils.convertStringToList(entity.getDeliveryOptions()))")
   Availability toAvailability(AvailabilityEntity entity);

   AvailabilityDTO toAvailabilityDTO(Availability availability);
}
