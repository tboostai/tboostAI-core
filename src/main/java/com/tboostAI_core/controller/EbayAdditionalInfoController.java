package com.tboostAI_core.controller;

import com.tboostAI_core.dto.EbayAdditionalInfoDTO;
import com.tboostAI_core.service.EbayAdditionalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebay")
@Tag(name = "Ebay archive info API", description = "API for accessing additional Ebay information stored for potential future use")
public class EbayAdditionalInfoController {

    private final EbayAdditionalInfoService ebayAdditionalInfoService;

    public EbayAdditionalInfoController(EbayAdditionalInfoService ebayAdditionalInfoService) {
        this.ebayAdditionalInfoService = ebayAdditionalInfoService;
    }

    @Operation(
            summary = "Get additional Ebay information by vehicle ID",
            description = "Fetches additional archived Ebay information associated with a specific vehicle ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ebay additional info found",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EbayAdditionalInfoDTO.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "itemId": "1234567890",
                                              "additionalInfo": "{\\"condition\\":\\"used\\",\\"shippingDetails\\":{\\"freeShipping\\":true,\\"deliveryTime\\":\\"3-5 days\\"}}"
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Ebay additional info not found")
    })
    @GetMapping("/additional")
    public ResponseEntity<EbayAdditionalInfoDTO> getEbayAdditionalInfoByVehicleId(Long vehicleId) {
        EbayAdditionalInfoDTO ebayAdditionalInfoDTO = ebayAdditionalInfoService.getEbayAdditionalInfoByVehicleId(vehicleId);
        return ebayAdditionalInfoDTO != null ? ResponseEntity.ok(ebayAdditionalInfoDTO) : ResponseEntity.notFound().build();
    }
}
