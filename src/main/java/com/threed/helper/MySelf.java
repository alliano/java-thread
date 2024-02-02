package com.threed.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySelf {

    public ExecutorService pray = Executors.newSingleThreadExecutor();

    public static MySelf needSomeone() {
        return new MySelf() ;
    }
}
