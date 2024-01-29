package com.threed.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SyncronusMethodCounter {

    private Long increment = 0L;

    public synchronized void increment() {
        this.increment++;
    }
}
