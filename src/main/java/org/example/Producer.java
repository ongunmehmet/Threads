package org.example;

import java.util.Queue;

public class Producer implements Runnable {
    private final Queue<String> queue;

    public Producer(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                produceData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void produceData() throws InterruptedException {
        synchronized (queue){
            if(queue.size()==10){
                System.out.println("In producer ,waiting");
                queue.wait();
            }
            Thread.sleep(1000);
            System.out.println("Producing data with id " + queue.size());
            queue.add("element_" + queue.size());

            if(queue.size() ==1){
                queue.notify();
            }
        }
    }
}
