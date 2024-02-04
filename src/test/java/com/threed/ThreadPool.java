package com.threed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.threed.helper.God;
import com.threed.helper.Slef;
import com.threed.helper.SomeOne;

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

    @Test
    public void executorServiceSingle() {
        ExecutorService singeleExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 1000; i++) {
            singeleExecutor.execute(() -> {
                System.out.println("Execute......");
            });
        }
        singeleExecutor.shutdown();
        singeleExecutor.close();
    }
    
    @Test
    public void executorServiceFix() {
        ExecutorService singeleExecutor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            singeleExecutor.execute(() -> {
                System.out.println("Execute......"+Thread.currentThread().getName());
            });
        }
        singeleExecutor.shutdown();
        singeleExecutor.close();
    }

    @Test @SneakyThrows
    public void testGetMyFurureSomeone(){
        Slef mySelf = Slef.needSomeone();
        Future<SomeOne> myFutureSomeone = mySelf.pray.submit(() -> {
                    Thread.sleep(5000L);
                    return God.giveSomeoneTheBest();
                });
        while (!myFutureSomeone.isDone()) {
            System.out.println("Wait........");
            Thread.sleep(1000L);
        }
        SomeOne mySomeOne = myFutureSomeone.get();
        System.out.println("My Future Girl");
        System.out.println("-----------------------------");
        System.out.println("Name : " + mySomeOne.getName());
        System.out.println("Age : " + mySomeOne.getAge());
        System.out.println("Brith date : " + mySomeOne.getBrithDate());
        
        mySelf.pray.shutdown();
        mySelf.pray.close();
    }

    @Test @SneakyThrows
    public void testCallable(){
        // membuat callable dengan labda dengan Return value berupa String
        Callable<String> callable = () -> {
            Thread.sleep(5000L);
            return "Hallo, Adinda";
        };

        ExecutorService singleThreadService = Executors.newSingleThreadExecutor();
        
        // melakukan eksekusi callable
        Future<String> future = singleThreadService.submit(callable);

        // mengecek apakah task callable sudah selesai di eksekusi
        while (!future.isDone()) {
            System.out.println("Wait........");
            Thread.sleep(2000L);
        }
        // future.get() -> mengambil return value dari callable
        System.out.println(future.get());
    }

    @Test @SneakyThrows
    public void invokeAll(){
        ExecutorService fixThreadPool = Executors.newFixedThreadPool(10);
        /**
         * task ini akan sleesai di eksekusi selama 2 detik
         * karena pada fixThreadPool kita membuat thread sebanyak 10
         * Berhubung kita memiliki 20 task maka tiap 1 detik task 
         * akan masuk di ke 10 thread dan akan di eksekusi selama 1 detik
         * dan detik kedua task 11 - 20 akan di eksekusi thread
         */
        List<Future<String>> results = fixThreadPool.invokeAll(callables());
        for (Future<String> future : results) {
            System.out.println(future.get());
        }
    }

    @SneakyThrows
    public List<Callable<String>> callables() {
        List<Callable<String>> callables = new ArrayList<Callable<String>>();
        for (int i = 0; i < 20; i++) {
            callables.add(() -> {
                Thread.sleep(1000L); // simulai proses kompleks
                return "task with Thread" + Thread.currentThread().getName();
            });
        }
        return callables;
    }

}
