package com.itprom.office.provider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itprom.office.service.WaitingRoutesService;

import bean.Route;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/routes")
public class RouteRest {
    private final WaitingRoutesService waitingRoutesService;

    @PostMapping(path = "route")
    public void addRoute(@RequestBody List<String> routeLocations) {
        Route route = waitingRoutesService.convertLocationsToRoute(routeLocations);
        waitingRoutesService.add(route);
    }
}
