package org.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.Main.countDownLatch;

public class Partitioner implements Runnable{
    private final List<Map.Entry<String,Integer>> intermadiateResult;
    private final List<List<Map.Entry<String,Integer>>> reducersInput;

    public Partitioner(List<Map.Entry<String, Integer>> intermadiateResult, List<List<Map.Entry<String, Integer>>> reducersInput) {
        this.intermadiateResult = intermadiateResult;
        this.reducersInput = reducersInput;
    }


    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<String> uniqueWords = intermadiateResult.stream()
                .map(Map.Entry::getKey)
                .distinct()
                .collect(Collectors.toList());
        for (String word : uniqueWords){
            List<Map.Entry<String,Integer>> reducerInput = intermadiateResult.stream()
                    .filter(entry->entry.getKey().equals(word))
                    .collect(Collectors.toList());
            reducersInput.add(reducerInput);
        }
        System.out.println(reducersInput);
    }
}
