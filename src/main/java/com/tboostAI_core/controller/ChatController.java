package com.tboostAI_core.controller;

import com.tboostAI_core.dto.AIChatResp;
import com.tboostAI_core.entity.request_entity.SubmitMessageRequest;
import com.tboostAI_core.service.impl.ChatServiceImpl;
import com.tboostAI_core.service.impl.VehicleBasicInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat")
@Tag(name = "Chat session APIs", description = "APIs for creating and deleting chat sessions")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Resource
    private ChatServiceImpl chatServiceImpl;

    @Operation(
            summary = "Create a new chat session",
            description = "Generates a new chat session ID and returns it."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session successfully created",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "\"12345-67890-ABCDE\""
                            )
                    )
            )
    })
    @PostMapping("/create-chat-session")
    public ResponseEntity<String> createSession() {
        logger.info("Start create session");
        String sessionId = chatServiceImpl.createNewSessionForChat();
        return ResponseEntity.ok(sessionId);
    }

    @Operation(
            summary = "Delete an existing chat session",
            description = "Deletes the specified chat session by session ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Session successfully deleted",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "\"12345-67890-ABCDE\""
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid session ID provided"),
            @ApiResponse(responseCode = "404", description = "Session ID not found")
    })
    @DeleteMapping("/delete-session")
    public ResponseEntity<String> deleteSession(@RequestParam String sessionId) {
        logger.info("Start delete session ID {}", sessionId);
        chatServiceImpl.deleteCurrentSessionForChat(sessionId);
        return ResponseEntity.ok(sessionId);
    }
    @PostMapping("/submit-message")
    public ResponseEntity<AIChatResp> submitMessage(@RequestBody SubmitMessageRequest request) {
        logger.info("Submit message request received: {}", request);

        try {
            AIChatResp aiChatResp = chatServiceImpl.submitMessage(request.getSessionId(), request.getMessage());

            if (aiChatResp == null) {
                logger.error("AIChatResp is null for session ID {}", request.getSessionId());
                return ResponseEntity.internalServerError().body(null);
            }

            return ResponseEntity.ok(aiChatResp);

        } catch (Exception e) {
            logger.error("Error while processing message for session ID {}: {}", request.getSessionId(), e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
