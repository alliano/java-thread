package com.threed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.threed.helper.God;
import com.threed.helper.MySelf;
import com.threed.helper.SomeOne;

import lombok.SneakyThrows;

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

    @Test @SneakyThrows
    public void testGetMyFurureSomeone(){
        MySelf mySelf = MySelf.needSomeone();
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
