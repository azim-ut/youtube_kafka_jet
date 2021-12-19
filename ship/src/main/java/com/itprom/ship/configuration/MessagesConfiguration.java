package com.itprom.ship.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import processor.MessagesConverter;

@Configuration
public class MessagesConfiguration {

    @Bean
    public MessagesConverter converter() {
        return new MessagesConverter();
    }
}
