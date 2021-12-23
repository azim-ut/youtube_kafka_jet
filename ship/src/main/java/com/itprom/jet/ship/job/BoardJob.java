package com.itprom.jet.ship.job;

import java.util.List;
import java.util.Optional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itprom.jet.common.bean.Board;
import com.itprom.jet.common.bean.RoutePath;
import com.itprom.jet.common.messages.BoardStateMessage;
import com.itprom.jet.common.processor.MessageConverter;
import com.itprom.jet.ship.provider.BoardProvider;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class BoardJob {

    private final BoardProvider boardProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageConverter messageConverter;

    @Scheduled(initialDelay = 0, fixedDelay = 250)
    public void fly() {
        boardProvider.getBoards().stream().
                filter(Board::hasRoute)
                .forEach(board -> {
                    board.getRoute()
                            .getPath()
                            .stream().filter(RoutePath::inProgress)
                            .findFirst()
                            .ifPresent(routePath -> {
                                board.calculatePosition(routePath);
                                routePath.addProgress(board.getSpeed());
                                if (routePath.done()) {
                                    board.setLocation(routePath.getTo().getName());
                                }
                            });
                });
    }

    @Async
    @Scheduled(initialDelay = 0, fixedDelay = 1000)
    public void notifyState() {
        boardProvider.getBoards().stream()
                .filter(Board::isBusy)
                .forEach(board -> {
                    Optional<RoutePath> route = board
                            .getRoute()
                            .getPath().stream()
                            .filter(RoutePath::inProgress)
                            .findAny();

                    if (route.isEmpty()) {
                        List<RoutePath> path = board.getRoute().getPath();
                        board.setLocation(path.get(path.size() - 1).getTo().getName());
                        board.setBusy(false);
                    }


                    BoardStateMessage msg = new BoardStateMessage(board);
                    kafkaTemplate.sendDefault(messageConverter.toJson(msg));
                });
    }
}
