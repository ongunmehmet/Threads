package org.example;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Main {
//    public static void main(String[] args) throws InterruptedException {
//        Thread thread = Thread.currentThread();
//        System.out.printf("Current Thread %s", thread.getName());
//
//        Thread.sleep(3000);
//        System.out.printf("Current Thread %s", thread.getName());
//    }
//    public static void main(String[] args) throws InterruptedException {
//    MyThread myThread = new MyThread();
//        myThread.start();
//    }
//    static class MyThread extends Thread{
//        @Override
//        public void run() {
//            setName("MyThread");
//            System.out.printf("Current Thread %s", Thread.currentThread().getName());
//        }
//    }

//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("1 Current Thread %s" +Thread.currentThread().getName());
//        Runnable runnable = () -> System.out.println("2 Current Thread %s" + Thread.currentThread().getName());
//        Thread thread = new Thread(runnable);
//        thread.setName("MyFuckingThread");
//        thread.start();
//        thread.join();
//        System.out.println("3 Current Thread %s"+Thread.currentThread().getName());
//    }

//    public static void main(String[] args) {
//        new Thread(new Watcher()).start();
//    }
//
//}
//public static void main(String[] args) {
//    Queue queue = new LinkedList<>();
//    Thread producer = new Thread(new Producer(queue));
//    Thread consumer = new Thread(new Consumer(queue));
//
//    producer.start();
//    consumer.start();
//}
    private static final String input = "a friend in need is a friend indeed";
    static final List<Map.Entry<String,Integer>> intermadiateResult = Collections.synchronizedList(new ArrayList<>());
    static final List<List<Map.Entry<String,Integer>>> reducedResult = Collections.synchronizedList(new ArrayList<>());
    static  final CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        List<String> inputList= Arrays.asList(input.split(" "));

        new Thread(new Mapper(inputList.subList(0,inputList.size()/2), intermadiateResult)).start();

         new Thread(new Mapper(inputList.subList(inputList.size()/2,inputList.size()), intermadiateResult)).start();


       Thread partitioner = new Thread(new Partitioner(intermadiateResult,reducedResult));
       partitioner.start();
       partitioner.join();
       for (List<Map.Entry<String,Integer>> reducerInput :reducedResult){
           Thread reducerThread =new Thread(new Reducer(reducerInput));
           reducerThread.start();
           reducerThread.join();
       }
       System.out.println("Test");
    }
}
