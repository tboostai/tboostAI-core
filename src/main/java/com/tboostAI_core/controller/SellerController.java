package com.tboostAI_core.controller;

import com.tboostAI_core.dto.SellerDTO;
import com.tboostAI_core.service.SellerService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/seller")
public class SellerController {

    @Resource
    private SellerService sellerService;

    @GetMapping("/{uuid}")
    public ResponseEntity<SellerDTO> getVehicleByVin(@PathVariable Long uuid) {
        SellerDTO sellerDTO = sellerService.getSellerById(uuid);
        return sellerDTO != null ? ResponseEntity.ok(sellerDTO) : ResponseEntity.notFound().build();
    }
}
