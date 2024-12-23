package com.tboostAI_core.controller;

import com.tboostAI_core.dto.VehiclePriceDTO;
import com.tboostAI_core.service.impl.PriceServiceImpl;
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
@RequestMapping("/price")
@Tag(name = "Price info related APIs", description = "APIs about price info")
public class PriceController {

    private final PriceServiceImpl priceServiceImpl;

    public PriceController(PriceServiceImpl priceServiceImpl) {
        this.priceServiceImpl = priceServiceImpl;
    }

    @Operation(
            summary = "Get price details by vehicle ID",
            description = "Retrieves a list of price details for a specific vehicle identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price details retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = VehiclePriceDTO.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "vehicle": {
                                                  "make": "Toyota",
                                                  "model": "Camry",
                                                  "year": 2020
                                                },
                                                "price": 25000.00,
                                                "currency": "USD",
                                                "convertedFromValue": 20000.00,
                                                "convertedFromCurrency": "EUR"
                                              }
                                            ]"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No price details found for the given vehicle ID")
    })
    @GetMapping("/{vehicleId}")
    public ResponseEntity<List<VehiclePriceDTO>> getPricesByVehicleId(@PathVariable Long vehicleId) {
        List<VehiclePriceDTO> vehiclePriceDTOList = priceServiceImpl.findAllPricesByVehicleId(vehicleId);

        if (vehiclePriceDTOList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(vehiclePriceDTOList);
        }
    }
}
