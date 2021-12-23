package com.itprom.jet.office.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itprom.jet.common.bean.Route;
import com.itprom.jet.common.bean.RoutePath;
import com.itprom.jet.common.bean.RoutePoint;
import com.itprom.jet.office.provider.AirPortsProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PathService {
    private final AirPortsProvider airPortsProvider;

    public RoutePath makePath(String from, String to){
        return new RoutePath(airPortsProvider.getRoutePoint(from), airPortsProvider.getRoutePoint(to), 0);
    }

    public Route convertLocationsToRoute(List<String> locations) {
        Route route = new Route();
        List<RoutePath> path = new ArrayList<>();
        List<RoutePoint> points = new ArrayList<>();

        locations.forEach(location -> {
            airPortsProvider.getPorts().stream()
                    .filter(airPort -> airPort.getName().equals(location))
                    .findFirst()
                    .ifPresent(airPort -> points.add(new RoutePoint(airPort)));
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
