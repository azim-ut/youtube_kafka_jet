package com.itprom.office.service;

import org.springframework.stereotype.Service;

import com.itprom.office.provider.AirPortsProvider;

import bean.RoutePath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathService {

    private final AirPortsProvider airPortsProvider;

    public RoutePath makePath(String from, String to) {
        return new RoutePath(airPortsProvider.getRoutePoint(from), airPortsProvider.getRoutePoint(to), 0);
    }
}
