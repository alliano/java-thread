package com.threed;
import java.lang.InterruptedException;

public class DaemonThread {

    public static void main(String... args){

        // ini bisa di sebut user thread 
        Thread thread = new Thread(() -> {
            System.out.println("jalan pada thread "+Thread.currentThread().getName());
            }, "user_thread");
        thread.start();

        // ini busa disebut deamon thread karna bisa dibilang thread ini akan berjalan di bacground
        // daemon thread bisa kita gunakan sebagai job tertentu yang mana job tersebut tidak terlalu di perdulikan
        // misal nya aja seperti proses export data ke file csv 

        Thread daemonThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            }catch(InterruptedException ITX) {
                ITX.printStackTrace();
            }
        }, "deamon_thread");
        // ini akan mngubah thread ini menjadi deamon thread yang mana proses eksekusi tread ini berada pada bacground dari app kita
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
