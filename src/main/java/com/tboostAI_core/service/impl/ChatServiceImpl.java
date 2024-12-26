package com.tboostAI_core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostAI_core.common.PromptTypeEnum;
import com.tboostAI_core.dto.AIChatResp;
import com.tboostAI_core.entity.request_entity.Message;
import com.tboostAI_core.service.ChatService;
import com.tboostAI_core.utils.CommonUtils;
import com.tboostAI_core.utils.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.tboostAI_core.common.GeneralConstants.OPENAI_ASSISTANT;

@Service
public class ChatServiceImpl implements ChatService {

    private final RedisServiceForOpenAIImpl redisServiceForOpenAIImpl;
    private final WebClientUtils webClientUtils;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Value("${Spring.microserver.service.tboostai.chat.host}")
    private String tboostAIChatHost;

    public ChatServiceImpl(RedisServiceForOpenAIImpl redisServiceForOpenAIImpl, WebClientUtils webClientUtils, ObjectMapper objectMapper) {
        this.redisServiceForOpenAIImpl = redisServiceForOpenAIImpl;
        this.webClientUtils = webClientUtils;
        this.objectMapper = objectMapper;
    }

    public String createNewSessionForChat() {
        logger.info("ChatServiceImpl:submitMessage - Start create new chat session");
        return redisServiceForOpenAIImpl.createNewSessionForChat(PromptTypeEnum.CHAT);
    }

    public void deleteCurrentSessionForChat(String sessionId) {
        logger.info("ChatServiceImpl:submitMessage - Delete current chat session");
        redisServiceForOpenAIImpl.deleteSession(sessionId);
    }

    public AIChatResp submitMessage(String sessionId, String contentFromUser) {
        logger.info("ChatServiceImpl:submitMessage - Start submit message");
        logger.info("ChatServiceImpl:submitMessage - First step: refresh chat session - {}", sessionId);
        List<Object> messageHistory = redisServiceForOpenAIImpl.getChatHistoryList(sessionId);
        List<Message> aiMessages = CommonUtils.addNewMessageToHistoryMessageList(contentFromUser, messageHistory);
        // Save user content to redis
        if (!aiMessages.isEmpty()) {
            redisServiceForOpenAIImpl.saveMessageToList(sessionId, aiMessages.get(aiMessages.size() - 1));
        }
        logger.info("ChatServiceImpl:submitMessage - Messages will send to LLM service are {}", aiMessages);
        Mono<AIChatResp> aiChatRespMono = webClientUtils.sendPostRequestInternal(tboostAIChatHost, aiMessages, AIChatResp.class);

        if (aiChatRespMono == null) {
            return null;
        }
        AIChatResp aiChatResp = aiChatRespMono.block();
        if (aiChatResp == null) {
            return null;
        }
        String assistantRespStr;
        try {
            assistantRespStr = objectMapper.writeValueAsString(aiChatResp);
        } catch (JsonProcessingException e) {
            logger.error("ChatServiceImpl:submitMessage - Write object as JSON string failed", e);
            return aiChatResp;
        }
        Message assistantResp = CommonUtils.generateMessage(OPENAI_ASSISTANT, assistantRespStr);
        logger.info("ChatServiceImpl:submitMessage - Messages response by AI assistant is {}", assistantResp);
        // Save assistant content to redis
        redisServiceForOpenAIImpl.saveMessageToList(sessionId, assistantResp);

        return aiChatResp;
    }


}
