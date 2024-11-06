package com.tboostAI_core.mapper;

import com.tboostAI_core.dto.PaymentInfoDTO;
import com.tboostAI_core.entity.PaymentInfoEntity;
import com.tboostAI_core.entity.inner_model.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaymentInfoMapper {

    PaymentInfoMapper INSTANCE = Mappers.getMapper(PaymentInfoMapper.class);

    @Mapping(target = "paymentInstructions", expression = "java(com.tboostAI_core.utils.CommonUtils.convertStringToList(entity.getPaymentInstructions()))")
    PaymentInfo toPaymentInfo(PaymentInfoEntity entity);

    List<PaymentInfo> toPaymentInfoList(List<PaymentInfoEntity> entities);

    PaymentInfoDTO toPaymentInfoDTO(PaymentInfo paymentInfo);

    List<PaymentInfoDTO> toPaymentInfoDTOList(List<PaymentInfo> paymentInfos);
}
