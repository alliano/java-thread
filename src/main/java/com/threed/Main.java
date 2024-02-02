package com.threed;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.threed.helper.God;
import com.threed.helper.MySelf;
import com.threed.helper.SomeOne;


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        MySelf alliano = MySelf.needSomeone();
        Future<SomeOne> myFutureSomeone = alliano.pray.submit(() -> {
                    Thread.sleep(5000L);
                    return God.giveSomeoneTheBest();
                });
        
        while (!myFutureSomeone.isDone()) {
            System.out.println("Waiting for God answer.....");
            Thread.sleep(1000L);
        }
        SomeOne mySomeOne = myFutureSomeone.get();
        System.out.println("Your Future Girl");
        System.out.println("-----------------------------");
        System.out.println("Name : " + mySomeOne.getName());
        System.out.println("Age : " + mySomeOne.getAge());
        System.out.println("Brith date : " + mySomeOne.getBrithDate());

        alliano.pray.shutdown();
        alliano.pray.close();
    }
}
