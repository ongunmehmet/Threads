package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class FileProccessor implements Runnable{
    private  final File file;
    private static final  String OUTPUT_PATH ="./src/main/output/";

    public FileProccessor(File file) {
        this.file = file;
    }

    private String hash(String input){
        if(input.isEmpty()){
            throw new RuntimeException("The lines is empty");
        }
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final  byte[] hashbayts = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbayts);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String bytesToHex(byte[] hashbayts) {
        StringBuilder hexString = new StringBuilder(2*hashbayts.length);
        for(int i=0;i<hashbayts.length;i++){
            String hex = Integer.toHexString(0xff & hashbayts[i]);
            if(hex.length()==1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return  hexString.toString();
    }

    @Override
    public void run() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH + file.getName()));
            Files.lines(Paths.get(file.getCanonicalPath()))
                    .map(this::hash)
                    .map(line -> line + "\n")
                    .forEach(el-> {
                        try {
                            writer.write(el);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


            writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "->processed file" + file.getName());

    }
}
