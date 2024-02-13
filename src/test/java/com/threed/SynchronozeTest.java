package com.threed;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;

public class SynchronozeTest {
    
    @Test @SneakyThrows
    public void testSemaphore() {
        final Semaphore semaphore = new Semaphore(5);
        ExecutorService fixThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            fixThreadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000L);
                    System.out.println("i have unlimeted love for safa...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
        fixThreadPool.awaitTermination(20, TimeUnit.SECONDS);
    }
}
