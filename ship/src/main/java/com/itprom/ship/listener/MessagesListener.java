package com.itprom.ship.listener;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.Message;
import processor.MessageProcessor;
import processor.MessagesConverter;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagesListener {

    private final MessagesConverter messagesConverter;

    @Autowired
    private Map<String, MessageProcessor<? extends Message>> processors = new HashMap<>();

    @KafkaListener(id = "BoardId", topics = "officeRoutes")
    public void radarListener(String data) {
        String code = messagesConverter.extractCode(data);
        try {
            processors.get(code).process(data);
        } catch (Exception e) {
            log.error("Code: " + code + " " + e.getLocalizedMessage());
        }
    }
}
