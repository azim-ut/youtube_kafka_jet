package com.itprom.office.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.OfficeStateMessage;
import processor.MessagesConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfficeSocketHandler extends TextWebSocketHandler {

    private final MessagesConverter messagesConverter;
    private final Cache<String, WebSocketSession> sessionCache;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            sessionCache.put(session.getId(), session);
        }
        if (message.getPayload().equals("update")) {
            kafkaTemplate.sendDefault(messagesConverter.toJson(new OfficeStateMessage()));
        }
    }
}
