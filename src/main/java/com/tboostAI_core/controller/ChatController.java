package com.tboostAI_core.controller;

import com.tboostAI_core.dto.AIChatResp;
import com.tboostAI_core.entity.request_entity.SubmitMessageRequest;
import com.tboostAI_core.service.impl.ChatServiceImpl;
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

    @Operation(
            summary = "Submit a message to the chat session",
            description = "Sends a user message to the AI chat session and receives a structured response, including the AI's message, whether the user's input was sufficient, the accuracy of the system's response, and the inferred search parameters for vehicles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message processed successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AIChatResp.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "content": "Got it! Let's find something that's tough and keeps your costs in check. Are you thinking of using it for rugged adventures, or more for everyday use?",
                                              "userContentSufficient": false,
                                              "systemAccurateEnough": false,
                                              "systemAccurateRate": "40%",
                                              "requestParams": {
                                                "make": ["Toyota"],
                                                "model": ["RAV4"],
                                                "minYear": 2015,
                                                "maxYear": 2023,
                                                "trim": [],
                                                "mileage": null,
                                                "minPrice": null,
                                                "maxPrice": null,
                                                "color": [],
                                                "bodyType": ["SUV"],
                                                "engineType": ["Gasoline"],
                                                "transmission": [],
                                                "drivetrain": [],
                                                "condition": [],
                                                "capacity": null
                                              }
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "error": "Invalid request body",
                                              "details": "Session ID cannot be null"
                                            }"""
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "error": "Internal server error",
                                              "details": "Unexpected error occurred while processing the message"
                                            }"""
                            )
                    )
            )
    })

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
