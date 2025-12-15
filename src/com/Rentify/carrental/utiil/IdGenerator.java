package com.Rentify.carrental.utiil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IdGenerator {
    private static final String record = "D:\\Project\\CarRental-System\\Car-Rental-System\\data.record_id.txt";

    private static int readLastNumber(String filePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            return Integer.parseInt(br.readLine().trim());
        }catch(Exception e){
            return 100;
        }
    }

    private static void saveLastNumber(String filePath,int num){
        try(FileWriter fw = new FileWriter(filePath, false)) {
            fw.write(String.valueOf(num));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String recordId(){
        int last = readLastNumber(record);
        int next = last+1;
        saveLastNumber(record,next);
        return "R"+next;
    }

}
