package com.threed.helper;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterReadWriteLock {

    private Long counter = 0L;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void increment() {
        try {
            // melakukan locking di proses write
            readWriteLock.writeLock().lock();
            counter++;
        }
        finally {
            // melakukan unlock
            readWriteLock.writeLock().unlock();
        }
    }

    public Long getCounter() {
        try {
            // melakuakn lock di proses read
            readWriteLock.readLock().lock();
            return counter;
        }
        finally {
            // melakukan unlock
            readWriteLock.readLock().unlock();
        }
    }
}
