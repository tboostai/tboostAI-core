package com.tboostAI_core.service;

import com.tboostAI_core.dto.AIChatResp;

public interface ChatService {

    String createNewSessionForChat();

    void deleteCurrentSessionForChat(String sessionId);

    AIChatResp submitMessage(String sessionId, String message);
}
