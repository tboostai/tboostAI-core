package com.tboostAI_core.controller;

import com.tboostAI_core.dto.PaymentInfoDTO;
import com.tboostAI_core.service.impl.PaymentInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment info related APIs", description = "APIs about payment info")
public class PaymentInfoController {

    private final PaymentInfoServiceImpl paymentInfoServiceImpl;

    public PaymentInfoController(PaymentInfoServiceImpl paymentInfoServiceImpl) {
        this.paymentInfoServiceImpl = paymentInfoServiceImpl;
    }

    @Operation(
            summary = "Get payment information by vehicle ID",
            description = "Retrieves a list of payment information for a specific vehicle identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment information retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            [
                                              {
                                                "paymentMethodType": "Credit Card",
                                                "paymentInstructions": ["Pay within 30 days", "Contact seller for further details"]
                                              },
                                              {
                                                "paymentMethodType": "PayPal",
                                                "paymentInstructions": ["Pay securely through PayPal"]
                                              }
                                            ]"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No payment information found for the given vehicle ID")
    })
    @GetMapping("/{vehicleId}")
    public ResponseEntity<List<PaymentInfoDTO>> getPaymentInfoByVehicleId(@PathVariable("vehicleId") Long vehicleId) {
        List<PaymentInfoDTO> paymentInfoDTOS = paymentInfoServiceImpl.getPaymentInfosByVehicleId(vehicleId);

        if (paymentInfoDTOS == null || paymentInfoDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(paymentInfoDTOS);
        }
    }
}