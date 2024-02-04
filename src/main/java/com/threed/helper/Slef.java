package com.threed.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Slef {

    public ExecutorService pray = Executors.newSingleThreadExecutor();

    public static Slef needSomeone() {
        return new Slef() ;
    }
}
