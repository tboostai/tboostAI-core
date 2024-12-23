package com.tboostAI_core.controller;

import com.tboostAI_core.dto.PostDTO;
import com.tboostAI_core.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@Tag(name = "Post info related APIs", description = "APIs about post info")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @Operation(
            summary = "Get post details by vehicle ID",
            description = "Retrieves post details for a specific vehicle identified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post details retrieved successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PostDTO.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "title": "Amazing Vehicle",
                                              "subtitle": "Limited Time Offer",
                                              "itemCreationDate": "2024-01-01T10:00:00Z",
                                              "itemEndDate": "2024-01-31T23:59:59Z",
                                              "buyingOptions": ["Buy Now", "Auction"],
                                              "itemAffiliateWebUrl": "https://affiliate.example.com/item123",
                                              "itemWebUrl": "https://example.com/item123",
                                              "seller": {
                                                "username": "JohnDoe123",
                                                "platform": "eBay",
                                                "feedbackPercentage": "99.5%",
                                                "feedbackScore": 1200
                                              }
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No post found for the given vehicle ID")
    })
    @GetMapping("/{vehicleId}")
    public ResponseEntity<PostDTO> getPostByVehicleId(@PathVariable("vehicleId") Long vehicleId) {
        PostDTO postDTO = postServiceImpl.getPostByVehicleId(vehicleId);
        return postDTO != null ? ResponseEntity.ok(postDTO) : ResponseEntity.notFound().build();
    }
}
