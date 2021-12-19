package com.itprom.office.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import bean.AirPort;
import bean.RoutePoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@ConfigurationProperties(prefix = "application")
public class AirPortsProvider {

    private final List<AirPort> ports = new ArrayList<>();

    public AirPort findAirPortAndRemoveBoard(String boardName) {
        AtomicReference<AirPort> res = new AtomicReference<>();
        ports.stream()
                .filter(airPort -> airPort.getBoards().contains(boardName))
                .findFirst()
                .ifPresent(airPort -> {
                    airPort.removeBoard(boardName);
                    res.set(airPort);
                });
        return res.get();
    }

    public AirPort getAirPort(String airPortName) {
        return ports.stream()
                .filter(airPort -> airPort.getName().equals(airPortName))
                .findFirst()
                .orElse(new AirPort());
    }

    public RoutePoint getRoutePoint(String portName) {
        AtomicReference<RoutePoint> res = new AtomicReference<>();
        getPorts().stream()
                .filter(airPort -> airPort.getName().equals(portName))
                .findFirst().ifPresent(airPort -> {
                    RoutePoint routePoint = new RoutePoint();
                    routePoint.setX(airPort.getX());
                    routePoint.setY(airPort.getY());
                    routePoint.setName(airPort.getName());
                    res.set(routePoint);
                });
        return res.get();
    }
}
