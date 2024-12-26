package com.tboostAI_core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostAI_core.common.PromptTypeEnum;
import com.tboostAI_core.entity.request_entity.Message;
import com.tboostAI_core.service.RedisServiceForOpenAI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import static com.tboostAI_core.common.GeneralConstants.*;

@Service
public class RedisServiceForOpenAIImpl implements RedisServiceForOpenAI {

    //TODO 1. Add refresh sessionId 2. Persist chat info
    private final LettuceConnectionFactory lettuceConnectionFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceForOpenAIImpl.class);

    public RedisServiceForOpenAIImpl(LettuceConnectionFactory lettuceConnectionFactory, RedisTemplate<String, Object> redisTemplate) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
        this.redisTemplate = redisTemplate;
    }

    // Create new chat session
    public String createNewSessionForChat(PromptTypeEnum promptType) {
        logger.info("Before create new session, redis host is {}, port is {}",
                lettuceConnectionFactory.getHostName(), lettuceConnectionFactory.getPort());
        String sessionId = UUID.randomUUID().toString();
        logger.info("RedisServiceForOpenAIImpl::createNewSessionForChat, SessionId is {}", sessionId);
        // Dynamically select the prompt based on input
        String systemPrompt = selectPrompt(promptType);
        logger.info("RedisServiceForOpenAIImpl::createNewSessionForChat, System prompt is {}", systemPrompt);
        // Generate system message using the selected prompt
        Message systemMessage = generateSystemMessage(systemPrompt);

        this.saveMessageToList(sessionId, systemMessage);
        return sessionId;
    }

    // Save chat history
    public void saveMessageToList(String sessionId, Message message) {
        logger.info("RedisServiceForOpenAIImpl:saveMessageToList - Session ID is {}", sessionId);
        String compressedMsg = compressMessage(message);
        logger.info("RedisServiceForOpenAIImpl:saveMessageToList - Session ID is {}", sessionId);
        redisTemplate.opsForList().rightPush(sessionId, compressedMsg);
        redisTemplate.expire(sessionId, CHAT_SESSION_TIMEOUT, TimeUnit.SECONDS);
    }

    // Get chat history
    public List<Object> getChatHistoryList(String sessionId) {
        List<Object> redisResp = redisTemplate.opsForList().range(sessionId, 0, -1);
        List<Object> decompressedList = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(redisResp).size(); i++) {
            if (redisResp.get(i) != null && redisResp.get(i) instanceof String) {
                Message message = decompressString((String) redisResp.get(i));
                decompressedList.add(message);
            }
        }
        logger.info("Session ID is {}, decompressed messages are {}", sessionId, decompressedList);
        return decompressedList;
    }

    // Delete chat session
    public void deleteSession(String sessionId) {
        redisTemplate.delete(sessionId);
    }

    // Compress Message object to string
    public String compressMessage(Message message) {
        if (message == null) {
            logger.info("Message is null");
            return null;
        }

        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize message", e);
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream;
        try {
            gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(jsonStr.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();
            return byteArrayOutputStream.toString(StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            logger.error("Failed to compress message", e);
        }
        return null;
    }

    // decompress string to Message object
    public Message decompressString(String compressedMessage) {
        if (compressedMessage == null || compressedMessage.isEmpty()) {
            logger.info("Compressed message is null or empty");
            return null;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedMessage.getBytes(StandardCharsets.ISO_8859_1));

        int bufferSize = Math.max(byteArrayInputStream.available(), STD_BUFFER_SIZE);

        GZIPInputStream gzipInputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            String jsonString = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

            return objectMapper.readValue(jsonString, Message.class);
        } catch (IOException e) {
            logger.error("Failed to decompress message", e);
        }


        return null;
    }

    private Message generateSystemMessage(String systemPrompt) {
        Message message = new Message();
        message.setRole(OPENAI_SYSTEM);
        message.setContent(systemPrompt);

        return message;
    }

    private String selectPrompt(PromptTypeEnum promptType) {
        return switch (promptType) {
            case CHAT -> OPENAI_CHAT_CONTENT_PROMPT;
            case LLM -> OPENAI_SYSTEM_LLM_MSG;
        };
    }
}
