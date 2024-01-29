package com.threed.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Ewallet {

    private Long balance = 0L;

    @SneakyThrows
    public static void tranferDeadLock(Ewallet from, Ewallet to, Long blance) {
        synchronized(from) {
            Thread.sleep(1000L);
            synchronized(to) {
                from.setBalance(from.getBalance() - blance);
                to.setBalance(to.getBalance() + blance);
            }
        }
    }

    @SneakyThrows
    public static void tranfer(Ewallet from, Ewallet to, Long blance) {
        synchronized(from) {
            Thread.sleep(1000L);
            from.setBalance(from.getBalance() - blance);
        }
        
        synchronized(to) {
            Thread.sleep(1000L);
            to.setBalance(to.getBalance() + blance);
        }
    }
}
