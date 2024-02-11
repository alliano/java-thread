package com.threed.helper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.Getter;

@Getter
public class CounterLock {
   
    private Long counter = 0L;

    private final Lock lock = new ReentrantLock();

    public void increment() {
        try {
            lock.lock();
            counter++;
        }
        finally {
            lock.unlock();
        }
    }
}
