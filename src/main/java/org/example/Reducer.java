package org.example;

import java.util.List;
import java.util.Map;

public class Reducer implements Runnable {

    private List<Map.Entry<String, Integer>> reducerInput;

    public Reducer(List<Map.Entry<String, Integer>> reducerInput) {
        this.reducerInput = reducerInput;
    }


    @Override
    public void run() {
        int S = 0;
    for (Map.Entry<String,Integer> entry : reducerInput){
        S+= entry.getValue();

    }
        System.out.println("The word: " + reducerInput.get(0).getKey()+" "+ + S);


    }
}
