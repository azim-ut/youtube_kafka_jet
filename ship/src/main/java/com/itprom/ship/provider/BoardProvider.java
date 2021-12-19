package com.itprom.ship.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import bean.Board;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@ConfigurationProperties(prefix = "application")
public class BoardProvider {

    private final List<Board> boards = new ArrayList<>();
}
