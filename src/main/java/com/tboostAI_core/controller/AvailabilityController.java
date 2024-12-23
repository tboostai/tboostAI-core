package com.tboostAI_core.controller;

import com.tboostAI_core.dto.AvailabilityDTO;
import com.tboostAI_core.service.impl.AvailabilityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
@Tag(name = "Vehicle availability info related APIs", description = "APIs about vehicle availability info")
public class AvailabilityController {

    @Resource
    private AvailabilityServiceImpl availabilityServiceImpl;

    @Operation(
            summary = "Get vehicle availability by ID",
            description = "Fetches the availability details of a vehicle based on its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability details found",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AvailabilityDTO.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "deliveryOptions": ["Standard Shipping", "Express Shipping"],
                                              "estimatedAvailabilityStatus": "In Stock",
                                              "estimatedAvailableQuantity": 10,
                                              "estimatedSoldQuantity": 5,
                                              "estimatedRemainingQuantity": 5
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Availability details not found")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable Long uuid) {
        AvailabilityDTO availabilityDTO = availabilityServiceImpl.getAvailabilityById(uuid);
        return availabilityDTO != null ? ResponseEntity.ok(availabilityDTO) : ResponseEntity.notFound().build();
    }
}
