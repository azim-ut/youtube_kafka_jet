package com.itprom.office.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import bean.Board;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class BoardsProvider {

    private final List<Board> boards = new ArrayList<>();
    private final Lock lock = new ReentrantLock(false);

    public Board getBoard(String boardName){
        return boards
                .stream().filter(board -> board.getName().equals(boardName))
                .findFirst()
                .orElse(null);
    }

    public void setBoard(Board board){
        try {
            lock.lock();
            Optional<Board> optional = boards.stream()
                    .filter(row -> board.getName().equals(row.getName()))
                    .findFirst();
            if(optional.isPresent()){
                int ind = boards.indexOf(optional.get());
                boards.set(ind, board);
            }else{
                boards.add(board);
            }
        } finally {
            lock.unlock();
        }
    }
}
