package com.tboostAI_core.controller;

import com.tboostAI_core.dto.SellerDTO;
import com.tboostAI_core.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/seller")
@Tag(name = "Seller info related APIs", description = "APIs about seller info")
public class SellerController {

    @Resource
    private SellerService sellerService;

    @Operation(
            summary = "Get seller details by ID",
            description = "Retrieves the details of a seller based on their unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seller details retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SellerDTO.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "username": "JohnDoe123",
                                              "platform": "eBay",
                                              "feedbackPercentage": "99.5%",
                                              "feedbackScore": 1200
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No seller details found for the given ID")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<SellerDTO> getVehicleByVin(@PathVariable Long uuid) {
        SellerDTO sellerDTO = sellerService.getSellerById(uuid);
        return sellerDTO != null ? ResponseEntity.ok(sellerDTO) : ResponseEntity.notFound().build();
    }
}
