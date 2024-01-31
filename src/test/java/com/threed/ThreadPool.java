package com.threed;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadPool {

    @Test
    public void threadPoolCreateTest() {
        // ThreadPool Setings
        int corePoolSize = 10;
        int maxPoolSize = 1000;
        int keepAliveTime = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, aliveTime, queue);
        Assertions.assertNotNull(threadPoolExecutor);
    }
}
