package com.tboostAI_core.controller;

import com.tboostAI_core.dto.PaymentInfoDTO;
import com.tboostAI_core.service.PaymentInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {

    private final PaymentInfoService paymentInfoService;

    public PaymentInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<List<PaymentInfoDTO>> getPaymentInfoByVehicleId(@PathVariable("vehicleId") Long vehicleId) {
        List<PaymentInfoDTO> paymentInfoDTOS = paymentInfoService.getPaymentInfosByVehicleId(vehicleId);

        if (paymentInfoDTOS == null || paymentInfoDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(paymentInfoDTOS);
        }
    }
}
