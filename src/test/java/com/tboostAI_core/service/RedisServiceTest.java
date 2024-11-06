package com.tboostAI_core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    private ListOperations<String, Object> listOperations;
    @InjectMocks
    @Spy
    private RedisServiceForOpenAI redisServiceForOpenAI;

    private static final String SESSION_ID = "test-session";
    private static final String MESSAGE_CONTENT = "test-message";


    @BeforeEach
    void setUp() throws JsonProcessingException {
        listOperations = Mockito.mock(ListOperations.class);
        // Mock RedisTemplate 的 opsForList 方法返回 listOperations
        Mockito.when(redisTemplate.opsForList()).thenReturn(listOperations);
        // Mock ListOperations 的 rightPush
        Mockito.when(listOperations.rightPush(Mockito.anyString(), Mockito.anyString())).thenReturn(1L);
        // Mock RedisTemplate 的 expire 方法
        Mockito.when(redisTemplate.expire(Mockito.anyString(), Mockito.anyLong(), Mockito.any())).thenReturn(true);
    }

    @Test
    void createNewSessionForChat() {
//        Mockito.doNothing().when(redisService).saveMessageToList(Mockito.anyString(), Mockito.anyString());
//        String sessionId = redisService.createNewSessionForChat();
//
//        assertNotNull(sessionId);
//
//        Mockito.verify(redisService, Mockito.times(1)).saveMessageToList(Mockito.anyString(), Mockito.anyString());

    }

    @Test
    void saveMessageToList() {
//        // 模拟 redisTemplate 的行为，返回 listOperations
//        Mockito.when(redisTemplate.opsForList()).thenReturn(listOperations);
//
//        // 执行保存操作
//        redisService.saveMessageToList();
//
//        // 验证 redisTemplate 是否保存了消息
//        Mockito.verify(listOperations).rightPush(Mockito.eq(SESSION_ID), Mockito.anyString());
//
//        // 验证设置过期时间
//        Mockito.verify(redisTemplate).expire(Mockito.eq(SESSION_ID), Mockito.anyLong(), Mockito.eq(TimeUnit.SECONDS));
    }




    @Test
    void getChatHistoryList() {
    }

    @Test
    void deleteSession() {
    }

    @Test
    void compressMessage() {
    }

    @Test
    void decompressString() {
    }
}