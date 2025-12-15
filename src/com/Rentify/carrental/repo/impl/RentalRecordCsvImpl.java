package com.Rentify.carrental.repo.impl;

import com.Rentify.carrental.exceptions.RentalException;
import com.Rentify.carrental.model.RentalRecordModel;
import com.Rentify.carrental.model.RentalStatus;
import com.Rentify.carrental.repo.RentalRecordRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalRecordCsvImpl implements RentalRecordRepo {
    private final Path csvpath;
    private final String HEADER = "id,carId,customerId,start,due,status,return,totalCost";

    public RentalRecordCsvImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void saveRecord(RentalRecordModel r)throws IOException{
        List<RentalRecordModel> list = findAll();
        list.add(r);
        ensureHeaderInFile();
        writeAll(list);
    }

    @Override
    public void updateRecord(RentalRecordModel r) throws IOException, RentalException{
        boolean isFound = false;
        List<RentalRecordModel> list = findAll();
        for(int i=0; i<list.size();i++){
            RentalRecordModel x = (RentalRecordModel) list.get(i);
            if(x.getId().equalsIgnoreCase(r.getId())){
                isFound = true;
                x.setId(r.getId());
                x.setCarId(r.getCarId());
                x.setCustomerId(r.getCustomerId());
                x.setStartDate(r.getStartDate());
                x.setDueDate(r.getDueDate());
                x.setStatus(r.getStatus());
                x.setReturnDate(r.getReturnDate());
                x.setTotalCost(r.getTotalCost());
                list.set(i,x);
            }
        }
        if(!isFound){
            throw new RentalException("rentalId is not matched"+r.getId());
        }
        writeAll(list);
    }


    private void writeAll(List<RentalRecordModel> list) throws IOException{
        ensureHeaderInFile();
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();
        for(RentalRecordModel r : list){
            String returnDate = (r.getReturnDate() == null) ? "" : r.getReturnDate().toString();
            String line = r.getId()+","+
                    r.getCarId()+","+
                    r.getCustomerId()+","+
                    r.getStartDate()+","+
                    r.getDueDate()+","+
                    r.getStatus()+","+
                    returnDate+","+
                    r.getTotalCost();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    @Override
    public List<RentalRecordModel> findAll() throws IOException {
        List<RentalRecordModel> list = new ArrayList<RentalRecordModel>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line= br.readLine())!=null){
            if(!line.trim().isEmpty()){
                RentalRecordModel r = parseIntoRentalRecord(line);
                list.add(r);
            }
        }
        br.close();
        return list;
    }

    private RentalRecordModel parseIntoRentalRecord(String line){
        String[] word = line.split(",", -1);
        RentalRecordModel r = new RentalRecordModel();
        r.setId(word[0]);
        r.setCarId(word[1]);
        r.setCustomerId(word[2]);
        r.setStartDate(LocalDate.parse(word[3]));
        r.setDueDate(LocalDate.parse(word[4]));
        r.setStatus(RentalStatus.valueOf(word[5]));
        String returnDateStr = word[6];
        LocalDate returnDate = returnDateStr.isEmpty() ? null : LocalDate.parse(returnDateStr);
        r.setReturnDate(returnDate);
        r.setTotalCost(Double.parseDouble(word[7]));

        return r;
    }
    private void ensureHeaderInFile() throws IOException{
        if(Files.notExists(csvpath)){
            Files.createDirectories(csvpath.getParent());
            BufferedWriter bw = Files.newBufferedWriter(csvpath);
            bw.write(HEADER);
            bw.newLine();
            bw.close();
        }
    }

}
