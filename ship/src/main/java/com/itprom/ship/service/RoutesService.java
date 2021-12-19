package com.itprom.ship.service;

import bean.RoutePath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class RoutesService {

    private final Lock lock = new ReentrantLock(false);
    private final static int capacity = 10;
    private final Queue<List<RoutePath>> queue = new LinkedBlockingQueue<>();

    public List<RoutePath> list(){
        return queue.poll();
    }

    public void add(List<RoutePath> route) {
        lock.lock();
        try {
            if (queue.size() < capacity) {
                queue.add(route);
            }
        } finally {
            lock.unlock();
        }
    }

    public List<RoutePath> poll() {
        lock.lock();
        try {
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
