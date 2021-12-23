package com.itprom.jet.ship.listener.processor;

import org.springframework.stereotype.Component;

import com.itprom.jet.common.messages.BoardStateMessage;
import com.itprom.jet.common.processor.MessageProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("BOARD_STATE")
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    @Override
    public void process(String jsonMessage) {
        //IGNORE
    }
}
