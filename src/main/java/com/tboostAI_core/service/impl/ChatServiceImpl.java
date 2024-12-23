package com.tboostAI_core.service.impl;

import com.tboostAI_core.dto.AIChatResp;
import com.tboostAI_core.service.ChatService;
import com.tboostAI_core.utils.WebClientUtils;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final RedisServiceForOpenAIImpl redisServiceForOpenAIImpl;
    private final WebClientUtils webClientUtils;

    public ChatServiceImpl(RedisServiceForOpenAIImpl redisServiceForOpenAIImpl, WebClientUtils webClientUtils) {
        this.redisServiceForOpenAIImpl = redisServiceForOpenAIImpl;
        this.webClientUtils = webClientUtils;
    }

    public String createNewSessionForChat() {

        return redisServiceForOpenAIImpl.createNewSessionForChat();
    }

    public void deleteCurrentSessionForChat(String sessionId) {

        redisServiceForOpenAIImpl.deleteSession(sessionId);
    }

    public AIChatResp submitMessage(String sessionId, String message) {
        return null;
    }
}
