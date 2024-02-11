package com.threed.helper;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AtomicCounter {
    
    // menggunakan salah satu class di atomic untuk menampung angka
    private AtomicLong counter = new AtomicLong(0);

    public void increment() {
        // melakukan increment pada counter
        counter.incrementAndGet();
    }

    public Long getCounter() {
        return counter.get();
    }
}
