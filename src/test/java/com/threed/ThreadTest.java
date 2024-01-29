package com.threed;

import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;

public class ThreadTest {

    private String name = null;

    private Object lock = new Object();

    @Test @SneakyThrows
    public void manualThreadComunicationTest(){
        Runnable runnable1 = () -> {
            // disini akan menngecek apakah name == null
            // jika null akan masuk di invinite loop
            // jika tidak null maka akan masuk ke baris kode selanjutnya
            while (name == null) {}
            System.out.println("Name : "+name);
        };

        Runnable runnable2 = () -> {
            name = "Allia Azahra";
        };

        Thread thread1 = new Thread(runnable1, "thread-1");
        Thread thread2 = new Thread(runnable2, "thread-2");

        thread2.start();
        thread1.start();

        thread2.join();
        thread1.join();
    }

    @Test @SneakyThrows
    public void testThreadComunitation(){
        Runnable runnable1 = new Runnable() {
            @Override @SneakyThrows
            public void run() {
                // disini kit melakukan lock
                // namun berhubung disni name.wait()
                // lock ini akan di skip(di unlock) dan thread akan ditruskan
                // hingga name.notify() dipanggil
                synchronized(lock) {
                    lock.wait();
                    System.out.println("Nama : "+name);
                }
            }
        };

        Runnable runnable2 = () -> {
            synchronized(lock) {
                name = "Allia Azahra";
                lock.notify();
            }
        };

        Thread thread1 = new Thread(runnable1, "thread-1");
        Thread thread2 = new Thread(runnable2, "thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
