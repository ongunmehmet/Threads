package org.example;

import java.util.Queue;

public class Consumer implements Runnable {
    private final Queue<String> queue;

    public Consumer(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consumeData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void consumeData() throws InterruptedException {
        synchronized (queue){
            if (queue.isEmpty()){
                System.out.println("consumer is weiting ....");
                queue.wait();
            }

            Thread.sleep(700);
            String data = queue.remove();
            System.out.println("Consumerd data"+data);
            if(queue.size()==9){
                queue.notify();
            }
            queue.notify();
        }
    }
}
