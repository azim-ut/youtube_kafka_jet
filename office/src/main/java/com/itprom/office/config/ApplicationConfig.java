package com.itprom.office.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.TimeUnit;

import processor.MessagesConverter;

@Configuration
public class ApplicationConfig {

    @Bean
    public MessagesConverter converter() {
        return new MessagesConverter();
    }

    @Bean
    public Cache<String, WebSocketSession> sessionCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .build();
    }


}
