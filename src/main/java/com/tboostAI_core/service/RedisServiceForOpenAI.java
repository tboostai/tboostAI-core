package com.tboostAI_core.service;

import com.tboostAI_core.common.PromptTypeEnum;
import com.tboostAI_core.entity.request_entity.Message;

import java.util.List;

public interface RedisServiceForOpenAI {
    // Create new chat session
    String createNewSessionForChat(PromptTypeEnum promptType);

    // Save chat history
    void saveMessageToList(String sessionId, Message message);

    // Get chat history
    List<Object> getChatHistoryList(String sessionId);

    // Delete chat session
    void deleteSession(String sessionId);

    // Compress Message object to string
    String compressMessage(Message message);

    // decompress string to Message object
    Message decompressString(String compressedMessage);
}
