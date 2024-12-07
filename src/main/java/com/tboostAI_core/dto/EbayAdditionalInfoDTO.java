package com.tboostAI_core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "EbayAdditionalInfoDTO", description = "DTO containing additional information about an eBay item")
public class EbayAdditionalInfoDTO {

    @Schema(description = "The unique identifier for the eBay item", example = "1234567890")
    private String itemId;

    @Schema(
            description = "Additional information about the eBay item in JSON format",
            example = "{\"condition\":\"refurbished\",\"warranty\":\"1 year\",\"shippingDetails\":{\"freeShipping\":true,\"deliveryTime\":\"3-5 days\"}}"
    )
    private String additionalInfo;
}
