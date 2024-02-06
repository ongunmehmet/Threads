package org.example;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("eroor is handled" + e.getMessage());
    }
}
