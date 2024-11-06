package com.tboostAI_core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostAI_core.entity.request_entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RedisServiceForOpenAI {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceForOpenAI.class);

    public RedisServiceForOpenAI(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Create new chat session
    public String createNewSessionForChat() {
        String sessionId = UUID.randomUUID().toString();
        Message systemMessage = generateSystemMessage();
        this.saveMessageToList(sessionId, systemMessage);
        return sessionId;
    }

    // Save chat history
    public void saveMessageToList(String sessionId, Message message) {
        String compressedMsg = compressMessage(message);
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

    private Message generateSystemMessage() {
        Message message = new Message();
        message.setRole(OPENAI_SYSTEM);
        message.setContent(com.tboostAI_core.common.GeneralConstants.OPENAI_SYSTEM_DEFAULT_MSG);

        return message;
    }
}
