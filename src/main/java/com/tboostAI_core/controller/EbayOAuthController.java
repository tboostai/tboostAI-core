package com.tboostAI_core.controller;

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
public class EbayOAuthController {


    // Logger 用于打印调试信息
    private static final Logger logger = LoggerFactory.getLogger(EbayOAuthController.class);

    // 定义验证令牌（与 eBay 开发者后台的验证令牌一致）
    @Value("${ebay.verification.token}")
    private String verificationToken;

    @Value("${ebay.notification.endpoint}")
    private String notificationEndpoint;

    // 定义接收授权码的回调地址
    @RequestMapping(value = "/ebay_oauth_callback", method = RequestMethod.GET)
    public String handleEbayOAuthCallback(@RequestParam Map<String, String> queryParams) {
        // 检查授权回调中是否有 authorization code
        if (queryParams.containsKey("code")) {
            String authorizationCode = queryParams.get("code");
            // 在这里处理授权码，比如保存它或继续用它获取access_token
            // ...
            return "Received authorization code: " + authorizationCode;
        } else {
            return "Error: Authorization code not found.";
        }
    }


    @RequestMapping(value = "/ebay_notification", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> ebayNotification(
            @RequestParam Map<String, String> queryParams,
            @RequestBody(required = false) Map<String, Object> body) {
        try {
            logger.info("Received a notification request");
            logger.info("Query params: {}", queryParams);
            logger.info("Request body: {}", body);

            if (queryParams.containsKey("challenge_code")) {
                String challengeCode = queryParams.get("challenge_code");
                String endpoint = notificationEndpoint + "/ebay_notification";
                String hashString = challengeCode + verificationToken + endpoint;
                String hashedValue = sha256(hashString);
                logger.info("Generated challenge response: {}", hashedValue);

                // 构建响应
                String responseBody = "{\"challengeResponse\":\"" + hashedValue + "\"}";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // 增加日志记录完整响应
                logger.info("Returning response to eBay:");
                logger.info("HTTP Status: 200");
                logger.info("Headers: {}", headers);
                logger.info("Response Body: {}", responseBody);

                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            } else {
                if (body != null && body.containsKey("userId")) {
                    String userId = body.get("userId").toString();
                    logger.info("Received account deletion notification for user: " + userId);
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
    // 生成 SHA-256 哈希值的方法
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
