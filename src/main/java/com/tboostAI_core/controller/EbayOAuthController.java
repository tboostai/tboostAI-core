package com.tboostAI_core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@SpringBootApplication
@RestController
@Tag(name = "Ebay Auth API", description = "APIs used for eBay Authorization and Notification handling")
public class EbayOAuthController {

    private static final Logger logger = LoggerFactory.getLogger(EbayOAuthController.class);

    @Value("${ebay.verification.token}")
    private String verificationToken;

    @Value("${ebay.notification.endpoint}")
    private String notificationEndpoint;

    @Operation(
            summary = "Handle eBay OAuth callback",
            description = "Handles the callback request from eBay OAuth with the authorization code"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authorization code successfully received"),
            @ApiResponse(responseCode = "400", description = "Missing authorization code in the callback")
    })
    @RequestMapping(value = "/ebay_oauth_callback", method = RequestMethod.GET)
    public String handleEbayOAuthCallback(@RequestParam Map<String, String> queryParams) {
        // Check if the callback contains an authorization code
        if (queryParams.containsKey("code")) {
            String authorizationCode = queryParams.get("code");
            // Handle the authorization code (e.g., save it or exchange for access token)
            return "Received authorization code: " + authorizationCode;
        } else {
            return "Error: Authorization code not found.";
        }
    }

    @Operation(
            summary = "Handle eBay notification requests",
            description = "Handles notification requests from eBay, including challenge responses and user account deletions"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification processed successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"challengeResponse\":\"hashed-challenge-code\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error during notification processing")
    })
    @RequestMapping(value = "/ebay_notification", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> ebayNotification(
            @RequestParam Map<String, String> queryParams,
            @RequestBody(required = false) Map<String, Object> body) {
        try {
            logger.info("Received a notification request");
            logger.info("Query params: {}", queryParams);
            logger.info("Request body: {}", body);

            // Handle challenge requests from eBay
            if (queryParams.containsKey("challenge_code")) {
                String challengeCode = queryParams.get("challenge_code");
                String endpoint = notificationEndpoint + "/ebay_notification";
                String hashString = challengeCode + verificationToken + endpoint;
                String hashedValue = sha256(hashString);
                logger.info("Generated challenge response: {}", hashedValue);

                // Build response for the challenge
                String responseBody = "{\"challengeResponse\":\"" + hashedValue + "\"}";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                logger.info("Returning response to eBay:");
                logger.info("HTTP Status: 200");
                logger.info("Headers: {}", headers);
                logger.info("Response Body: {}", responseBody);

                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            } else {
                // Handle account deletion notifications or other payloads
                if (body != null && body.containsKey("userId")) {
                    String userId = body.get("userId").toString();
                    logger.info("Received account deletion notification for user: {}", userId);
                } else {
                    logger.warn("No userId found in the request body");
                }
                return ResponseEntity.ok("{\"status\":\"success\"}");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error while processing the request: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\":\"error\", \"message\":\"Internal server error\"}");
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\":\"error\", \"message\":\"Unexpected internal error\"}");
        }
    }

    /**
     * Generates a SHA-256 hash value for the given input string.
     *
     * @param input The input string to hash
     * @return The SHA-256 hash value
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    private String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
