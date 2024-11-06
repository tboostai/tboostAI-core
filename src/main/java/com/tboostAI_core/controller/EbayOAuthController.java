package com.tboostAI_core.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    private final String verificationToken = "uKDX14F4xrez27WVj7YRIsy30TJthXFBbOmmOzsEVuQCvwrJddcJhFsD4UCkVoHR";

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


    // 接收 eBay 通知的 endpoint
    @RequestMapping(value = "/ebay_notification", method = {RequestMethod.GET, RequestMethod.POST})
    public String ebayNotification(@RequestParam Map<String, String> queryParams, @RequestBody(required = false) Map<String, Object> body) {
        try {
            // 记录收到通知的日志
            logger.info("Received a notification request");
            logger.info("Query params: " + queryParams);
            logger.info("Request body: " + body);

            // 处理 challenge_code 验证请求
            if (queryParams.containsKey("challenge_code")) {
                String challengeCode = queryParams.get("challenge_code");
                String endpoint = "https://b293-2605-8d80-482-9749-d48f-eae2-4742-ac4d.ngrok-free.app/ebay_notification"; // 替换为你的实际 endpoint 地址
                String hashString = challengeCode + verificationToken + endpoint;
                String hashedValue = sha256(hashString);
                logger.info("Generated challenge response: {}", hashedValue);
                return "{\"challengeResponse\":\"" + hashedValue + "\"}";
            } else {
                // 处理账户删除通知
                if (body != null && body.containsKey("userId")) {
                    String userId = body.get("userId").toString(); // 获取用户 ID
                    logger.info("Received account deletion notification for user: " + userId);
                    // 在这里执行删除用户数据的逻辑
                } else {
                    logger.warn("No userId found in the request body");
                }
                return "{\"status\":\"success\"}";
            }
        } catch (NoSuchAlgorithmException e) {
            // 使用 log4j 的 error 方法记录错误
            logger.error("Error while processing the request: ", e);
            return "{\"status\":\"error\", \"message\":\"Internal server error\"}";
        } catch (Exception e) {
            // 捕获所有其他异常并记录
            logger.error("Unexpected error: ", e);
            return "{\"status\":\"error\", \"message\":\"Unexpected internal error\"}";
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
