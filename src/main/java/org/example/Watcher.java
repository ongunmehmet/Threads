package org.example;

import java.io.File;
import java.util.Arrays;

class Watcher implements Runnable{

    @Override
    public void run() {
        File inputDir = new File("./src/main/resources");
        while (true){ if(inputDir.listFiles().length != 0){
            Arrays.stream(inputDir.listFiles()).forEach(file -> {
                Thread t =new Thread(new FileProccessor(file));
            t.setUncaughtExceptionHandler(new ExceptionHandler());
            t.start();});

        }
            sleep();
        }
    }
    private void sleep(){
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
