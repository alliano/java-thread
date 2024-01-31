package com.threed;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;

public class ThreadPool {

    @Test
    public void threadPoolCreateTest() {
        /**
         * ini artinya ketika ThreadPoolExecutor dibuat
         * maka minimal thread yang akan dibuat adalah 10 thread
         */
        int corePoolSize = 10;
        // makdimal thread yang dibuat 100 thread
        int maxPoolSize = 100;
        /**
         * ini tergantung pada TimeUnit
         * jika TimeUnit nya TimeUnit.HOURS
         * maka artinya 1 hari
         * 
         * jika TimeUnit.MINUTES maka artinya 1 menit
         */
        int keepAliveTime = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        // ini artinya hanya menerima 100 antrian
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, aliveTime, queue);
        Assertions.assertNotNull(threadPoolExecutor);

        Runnable runnable = () -> {
            System.out.println("Task Executed.......");
        };
        threadPoolExecutor.execute(runnable);
    }

    @Test
    public void threadPoolExecuteor() {
     
        int corePoolSize = 10;
        int maxPoolSize = 100;
        
        int keepAliveTime = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, aliveTime, queue);
        Assertions.assertNotNull(threadPoolExecutor);

        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        threadPoolExecutor.execute(runable());
        // mematikan TthreadPool
        threadPoolExecutor.shutdown();
    }

    public Runnable runable() {
        return () -> {
            System.out.println("Task Executed by "+Thread.currentThread().getName());
        };
    }

    @Test @SneakyThrows
    public void testRejectedHandler(){
        int corePoolSize = 10;
        int maxPoolSize = 100;
        int keepAlive = 1;
        TimeUnit aliveTime = TimeUnit.MINUTES;
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
        RejectedExecutionHandler rejectedHandler = new LoggerRejectedHandler();

        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive, aliveTime, queue, rejectedHandler);
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive, aliveTime, queue, rejectedHandler);
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor1.execute(() -> {
                System.out.println("Task "+Thread.currentThread().getName());
            });
            threadPoolExecutor2.execute(() -> {
                System.out.println("Task "+Thread.currentThread().getName());
            });
        }
        
        threadPoolExecutor1.shutdown();
        threadPoolExecutor1.close();
        threadPoolExecutor2.shutdown();
        threadPoolExecutor2.close();
    }
    
    public static class LoggerRejectedHandler implements RejectedExecutionHandler {
        
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
           System.out.println("Task "+runnable+" Is rejected....");
        }
    }
}
