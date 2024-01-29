package com.threed.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class SyncronizeStatementCounter {
    
    private Long counter = 0L;

    private Long counter2 = 0L;

    private Long counter3 = 0L;

    public void increment() {
        counter2++;
        synchronized (this){
            this.counter++;
        }
        counter3++;
    }
}
