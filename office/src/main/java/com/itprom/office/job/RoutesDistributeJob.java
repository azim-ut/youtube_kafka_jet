package com.itprom.office.job;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itprom.office.provider.AirPortsProvider;
import com.itprom.office.provider.BoardsProvider;
import com.itprom.office.service.PathService;
import com.itprom.office.service.WaitingRoutesService;

import bean.AirPort;
import bean.Board;
import bean.Route;
import bean.RoutePath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.AirPortStateMessage;
import messages.OfficeRouteMessage;
import processor.MessagesConverter;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RoutesDistributeJob {

    private final PathService pathService;
    private final BoardsProvider boardsProvider;
    private final WaitingRoutesService waitingQueueService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagesConverter messagesConverter;
    private final AirPortsProvider airPortsProvider;

    @Scheduled(initialDelay = 500, fixedDelay = 2500)
    private void distribute() {
        waitingQueueService.list()
                .stream().filter(Route::notAssigned)
                .forEach(route -> {
                    String startLocation = route.getPath().get(0).getFrom().getName();
                    boardsProvider.getBoards().stream()
                            .filter(board -> board.getLocation().equals(startLocation))
                            .findFirst()
                            .ifPresent(board -> sendBoardByRoute(route, board));

                    if (route.notAssigned()) {
                        boardsProvider.getBoards().stream()
                                .filter(Board::noBusy)
                                .findFirst()
                                .ifPresent(board -> {
                                    String curLocation = board.getLocation();
                                    if (!startLocation.equals(curLocation)) {
                                        RoutePath routePath = pathService.makePath(curLocation, startLocation);
                                        route.getPath().add(0, routePath);
                                    }
                                    sendBoardByRoute(route, board);
                                });
                    }
                });
    }

    private void sendBoardByRoute(Route route, Board board) {
        route.setBoardName(board.getName());
        AirPort airPort = airPortsProvider.findAirPortAndRemoveBoard(board.getName());
        board.setLocation(null);
        kafkaTemplate.sendDefault(messagesConverter.toJson(new OfficeRouteMessage(route)));
        kafkaTemplate.sendDefault(messagesConverter.toJson(new AirPortStateMessage(airPort)));
    }

}
