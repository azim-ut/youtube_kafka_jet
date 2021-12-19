package com.itprom.office.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.itprom.office.provider.AirPortsProvider;

import bean.Route;
import bean.RoutePath;
import bean.RoutePoint;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaitingRoutesService {

    private final AirPortsProvider airPortsProvider;

    private final Lock lock = new ReentrantLock(false);
    private final List<Route> list = new ArrayList<>();

    public List<Route> list() {
        return List.copyOf(list);
    }

    public void add(Route route) {
        try {
            lock.lock();
            list.add(route);
        } finally {
            lock.unlock();
        }
    }

    public void remove(Route route) {
        try {
            lock.lock();
            list.removeIf(route::equals);
        } finally {
            lock.unlock();
        }
    }

    public Route convertLocationsToRoute(List<String> locations) {
        Route route = new Route();
        List<RoutePath> path = new ArrayList<>();
        List<RoutePoint> points = new ArrayList<>();
        locations.forEach(location -> {
            airPortsProvider.getPorts().stream()
                    .filter(airPort -> airPort.getName().equals(location))
                    .findFirst()
                    .ifPresent(airPort -> {
                        RoutePoint point = new RoutePoint();
                        point.setX(airPort.getX());
                        point.setY(airPort.getY());
                        point.setName(airPort.getName());
                        points.add(point);
                    });
        });

        for (int i = 0; i < points.size() - 1; i++) {
            path.add(new RoutePath());
        }

        path.forEach(row -> {
            int ind = path.indexOf(row);
            if (row.getFrom() == null) {
                row.setFrom(points.get(ind));
                if (points.size() > ind + 1) {
                    row.setTo(points.get(ind + 1));
                } else {
                    row.setTo(points.get(ind));
                }
            }
        });
        route.setPath(path);

        return route;
    }
}
