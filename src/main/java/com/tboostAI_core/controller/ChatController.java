package com.tboostAI_core.controller;

import com.tboostAI_core.service.VehicleBasicInfoService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Resource
    private VehicleBasicInfoService vehicleBasicInfoService;

    @PostMapping("/create-session")
    public ResponseEntity<String> createSession() {
        logger.info("Start create session");
        String sessionId = vehicleBasicInfoService.createNewSessionForChat();
        return ResponseEntity.ok(sessionId);
    }

    @DeleteMapping("/delete-session")
    public ResponseEntity<String> deleteSession(@RequestParam String sessionId) {

        logger.info("Start delete session ID {}", sessionId);
        vehicleBasicInfoService.deleteCurrentSessionForChat(sessionId);
        return ResponseEntity.ok(sessionId);
    }
}