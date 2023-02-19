package com.threed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestThred {
    
    public static void main(String... args) {
        SpringApplication.run(TestThred.class, args);

        String theredName = Thread.currentThread().getName();
        System.out.println(theredName);

    }
}

