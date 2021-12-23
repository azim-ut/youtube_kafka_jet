package com.itprom.jet.common.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AirPort {
    private String name;
    private List<String> boards = new ArrayList<>();
    private int x;
    private int y;

    public void addBoard(String boardName) {
        int ind = boards.indexOf(boardName);
        if (ind >= 0) {
            boards.set(ind, boardName);
        }else{
            boards.add(boardName);
        }
    }

    public void removeBoard(String boardName){
        boards.remove(boardName);
    }
}
