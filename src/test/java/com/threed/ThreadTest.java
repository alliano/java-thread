package com.threed;

import java.util.Timer;
import java.util.TimerTask;

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

    @Test @SneakyThrows
    public void notifyAllTest() {
        Thread thread1 = new Thread(() -> extracted(), "thread-1");
        Thread thread2 = new Thread(() -> extracted(), "thread-2");
        Thread thread3 = new Thread(() -> extracted(), "thread-3");

        Thread thread4 = new Thread(() -> {
            synchronized(lock) {
                name = "Abdillah Alli";
                lock.notifyAll();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }

    @SneakyThrows
    private void extracted() {
        synchronized(lock) {
            lock.wait();
            System.out.println("Name : "+name);
        }
    }

    @Test @SneakyThrows
    public void delayJob(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Job one executed......");
            }
        };

        Timer timer = new Timer();
        /**
         * timer task akan dieksekusi
         * setelah 5 detik
         */
        timer.schedule(timerTask, 5000L);

        Thread.sleep(6000L);
    }

    @Test @SneakyThrows
    public void repetedJob(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Job repeted executed.....");
            }
        };

        Timer timer = new Timer();
        /**
         * timerTask akan dieksekusi 1 detik sekali
         * dan diulangi eksekusi tersebut setelah 2 detik
         * 
         * simpelnya eksekusi setelh 1 detik dan istirahat selama 2 detik
         * dan proses tersebut berulang-ulang kali
         */
        timer.schedule(timerTask, 1000L, 2000L);

        Thread.sleep(5000L);
    }
}
