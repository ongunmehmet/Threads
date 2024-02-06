package org.example;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.example.Main.countDownLatch;


public class Mapper implements Runnable{
    private final List<String> input;
    private final List<Map.Entry<String,Integer>> intermadiateResult;

    public Mapper(List<String> input, List<Map.Entry<String, Integer>> intermadiateResult) {
        this.input = input;
        this.intermadiateResult = intermadiateResult;
    }


    @Override
    public void run() {
        for (String word : input){
            AbstractMap.SimpleEntry<String,Integer> entry= new AbstractMap.SimpleEntry<>(word,1);
            intermadiateResult.add(entry);

        }
        countDownLatch.countDown();
        System.out.println(intermadiateResult);
    }
}
