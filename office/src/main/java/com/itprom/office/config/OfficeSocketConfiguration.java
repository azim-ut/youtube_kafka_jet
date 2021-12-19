package com.itprom.office.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.github.benmanes.caffeine.cache.Cache;
import com.itprom.office.provider.AirPortsProvider;

import lombok.RequiredArgsConstructor;
import processor.MessagesConverter;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class OfficeSocketConfiguration implements WebSocketConfigurer {

    private final MessagesConverter messagesConverter;
    private final Cache<String, WebSocketSession> sessionCache;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new OfficeSocketHandler(messagesConverter, sessionCache, kafkaTemplate), "/websocket")
                .setAllowedOrigins("*");
    }
}
