package com.tboostAI_core.dto;

import com.tboostAI_core.entity.inner_model.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "PostDTO", description = "DTO representing a post, including item details and seller information")
public class PostDTO {

    @Schema(description = "The title of the post", example = "Amazing Product for Sale")
    private String title;

    @Schema(description = "The subtitle or brief description of the post", example = "Limited Time Offer")
    private String subtitle;

    @Schema(description = "The date when the item was created", example = "2024-01-01T10:00:00Z")
    private String itemCreationDate;

    @Schema(description = "The date when the item listing ends", example = "2024-01-31T23:59:59Z")
    private String itemEndDate;

    @Schema(
            description = "List of buying options available for the item",
            example = "[\"Buy Now\", \"Auction\"]"
    )
    private List<String> buyingOptions;

    @Schema(description = "Affiliate link to the item's web page", example = "https://affiliate.example.com/item123")
    private String itemAffiliateWebUrl;

    @Schema(description = "Direct URL to the item's web page", example = "https://example.com/item123")
    private String itemWebUrl;

    @Schema(description = "Information about the seller of the item")
    private Seller seller;
}
