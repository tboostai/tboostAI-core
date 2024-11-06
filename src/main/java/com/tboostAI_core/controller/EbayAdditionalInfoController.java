package com.tboostAI_core.controller;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;
import com.tboostAI_core.service.EbayAdditionalInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebay")
public class EbayAdditionalInfoController {

    private final EbayAdditionalInfoService ebayAdditionalInfoService;
    public EbayAdditionalInfoController(EbayAdditionalInfoService ebayAdditionalInfoService) {
        this.ebayAdditionalInfoService = ebayAdditionalInfoService;
    }

    public ResponseEntity<EbayAdditionalInfoDTO> getEbayAdditionalInfoByVehicleId(Long vehicleId) {

        EbayAdditionalInfoDTO ebayAdditionalInfoDTO = ebayAdditionalInfoService.getEbayAdditionalInfoByVehicleId(vehicleId);

        return ebayAdditionalInfoDTO != null ? ResponseEntity.ok(ebayAdditionalInfoDTO) : ResponseEntity.notFound().build();
    }
}
