package com.itprom.ship.listener.process;

import org.springframework.stereotype.Component;

import com.itprom.ship.provider.BoardProvider;

import bean.Route;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.OfficeRouteMessage;
import processor.MessageProcessor;
import processor.MessagesConverter;

@Slf4j
@Component("OFFICE_ROUTE")
@RequiredArgsConstructor
public class OfficeRouteProcessor implements MessageProcessor<OfficeRouteMessage> {

    private final MessagesConverter messagesConverter;
    private final BoardProvider boardProvider;

    @Override
    public void process(String jsonMessage) {
        OfficeRouteMessage msg = messagesConverter.extractMessage(jsonMessage, OfficeRouteMessage.class);
        Route route = msg.getRoute();
        boardProvider.getBoards()
                .stream()
                .filter(board -> !board.isBusy() && route.getBoardName().equals(board.getName()))
                .findFirst()
                .ifPresent(board -> {
                    board.setRoute(route);
                    board.setBusy(true);
                });
    }
}
