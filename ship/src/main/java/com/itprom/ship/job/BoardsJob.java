package com.itprom.ship.job;

import java.util.List;
import java.util.Optional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itprom.ship.provider.BoardProvider;

import bean.Board;
import bean.RoutePath;
import lombok.RequiredArgsConstructor;
import messages.BoardStateMessage;
import processor.MessagesConverter;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class BoardsJob {

    private final BoardProvider boardProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessagesConverter messagesConverter;

    @Scheduled(initialDelay = 0, fixedRate = 250)
    public void fly() {
        boardProvider.getBoards()
                .stream()
                .filter(Board::hasRoute)
                .forEach(board -> {
                    board.getRoute()
                            .getPath()
                            .stream()
                            .filter(RoutePath::inProgress)
                            .findFirst()
                            .ifPresent(route -> {
                                board.calculatePosition(route);
                                route.addProgress(board.getSpeed());
                                if (route.done()) {
                                    board.setLocation(route.getTo().getName());
                                }
                            });
                });
    }

    @Async
    @Scheduled(initialDelay = 0, fixedRate = 1000)
    public void notifyState() {
        boardProvider.getBoards()
                .stream()
                .filter(Board::isBusy)
                .forEach(board -> {
                    Optional<RoutePath> route = board
                            .getRoute()
                            .getPath().stream()
                            .filter(RoutePath::inProgress)
                            .findAny();

                    if (route.isEmpty()) {
                        List<RoutePath> path = board.getRoute().getPath();
                        board.setLocation(path.get(path.size()-1).getTo().getName());
                        board.setBusy(false);
                    }
                    BoardStateMessage msg = new BoardStateMessage(board);
                    kafkaTemplate.sendDefault(messagesConverter.toJson(msg));
                });
    }
}
