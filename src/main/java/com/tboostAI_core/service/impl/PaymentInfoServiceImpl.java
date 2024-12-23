package com.tboostAI_core.service.impl;

import com.tboostAI_core.dto.PaymentInfoDTO;
import com.tboostAI_core.entity.PaymentInfoEntity;
import com.tboostAI_core.entity.inner_model.PaymentInfo;
import com.tboostAI_core.mapper.PaymentInfoMapper;
import com.tboostAI_core.repository.PaymentInfoRepo;
import com.tboostAI_core.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    private final PaymentInfoRepo paymentInfoRepo;

    public PaymentInfoServiceImpl(PaymentInfoRepo paymentInfoRepo) {
        this.paymentInfoRepo = paymentInfoRepo;
    }

    public List<PaymentInfoDTO> getPaymentInfosByVehicleId(Long vehicleId) {
        List<PaymentInfoEntity> paymentInfoDTOS = paymentInfoRepo.findByVehicleUuid(vehicleId);
        List<PaymentInfo> paymentInfos = PaymentInfoMapper.INSTANCE.toPaymentInfoList(paymentInfoDTOS);

        return PaymentInfoMapper.INSTANCE.toPaymentInfoDTOList(paymentInfos);
    }
}
