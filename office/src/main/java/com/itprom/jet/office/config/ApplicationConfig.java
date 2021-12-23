package com.itprom.jet.office.config;

import java.net.http.WebSocket;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.itprom.jet.common.processor.MessageConverter;

@Configuration
public class ApplicationConfig {

    @Bean
    public MessageConverter converter() {
        return new MessageConverter();
    }

    @Bean
    public Cache<String, WebSocketSession> sessionCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
    }

}
