package com.itprom.ship.listener.process;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import messages.BoardStateMessage;
import processor.MessageProcessor;

@Slf4j
@Component("BOARD_STATE")
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {
    @Override
    public void process(String jsonMessage) {
        //IGNORE
    }
}
