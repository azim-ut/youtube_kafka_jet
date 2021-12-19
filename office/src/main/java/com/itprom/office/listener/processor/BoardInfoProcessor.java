package com.itprom.office.listener.processor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.itprom.office.provider.AirPortsProvider;
import com.itprom.office.provider.BoardsProvider;
import com.itprom.office.service.WaitingRoutesService;

import bean.AirPort;
import bean.Board;
import bean.Route;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.AirPortStateMessage;
import messages.BoardStateMessage;
import processor.MessageProcessor;
import processor.MessagesConverter;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardInfoProcessor implements MessageProcessor<BoardStateMessage> {

    private final MessagesConverter messagesConverter;
    private final WaitingRoutesService waitingQueueService;
    private final BoardsProvider boardsProvider;
    private final AirPortsProvider airPortsProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        BoardStateMessage message = messagesConverter.extractMessage(jsonMessage, BoardStateMessage.class);
        Board board = message.getBoard();
        Board previous = boardsProvider.getBoard(board.getName());
        AirPort airPort = airPortsProvider.getAirPort(board.getLocation());

        boardsProvider.setBoard(board);
        if (previous != null && board.hasRoute() && !previous.hasRoute()) {
            Route route = board.getRoute();
            waitingQueueService.remove(route);
        }

        if (previous == null || !board.isBusy() && previous.isBusy()) {
            airPort.addBoard(board.getName());
            kafkaTemplate.sendDefault(messagesConverter.toJson(new AirPortStateMessage(airPort)));
        }
    }
}
