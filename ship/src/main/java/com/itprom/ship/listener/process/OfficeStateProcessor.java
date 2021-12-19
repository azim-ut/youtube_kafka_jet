package com.itprom.ship.listener.process;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.itprom.ship.provider.BoardProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.BoardStateMessage;
import messages.OfficeStateMessage;
import processor.MessageProcessor;
import processor.MessagesConverter;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {

    private final MessagesConverter messagesConverter;
    private final BoardProvider boardProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void broadCastShipsInfo() {
        boardProvider.getBoards().forEach(board -> {
            kafkaTemplate.sendDefault(messagesConverter.toJson(new BoardStateMessage(board)));
        });
    }

    @Override
    public void process(String jsonMessage) {
        this.broadCastShipsInfo();
    }
}
