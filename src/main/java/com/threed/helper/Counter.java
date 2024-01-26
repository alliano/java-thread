package com.threed.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class Counter {

    private Long increment = 0L;

    public void increment() {
        this.increment++;
    }
}
