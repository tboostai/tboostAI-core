package com.tboostAI_core.controller;

import com.tboostAI_core.dto.VehiclePriceDTO;
import com.tboostAI_core.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/price")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<List<VehiclePriceDTO>> getPricesByVehicleId(@PathVariable Long vehicleId) {
        List<VehiclePriceDTO> vehiclePriceDTOList = priceService.findAllPricesByVehicleId(vehicleId);

        if (vehiclePriceDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(vehiclePriceDTOList);
        }
    }
}
