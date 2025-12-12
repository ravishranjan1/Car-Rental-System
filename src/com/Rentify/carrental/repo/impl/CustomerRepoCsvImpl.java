package com.Rentify.carrental.repo.impl;

import com.Rentify.carrental.exceptions.CustomerAlreadyPresentException;
import com.Rentify.carrental.model.CustomerModel;
import com.Rentify.carrental.repo.CustomerRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoCsvImpl implements CustomerRepo {
    private final Path csvpath;
    private final String HEADER = "id, name, phone, email";

    public CustomerRepoCsvImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void addCustomer(CustomerModel c) throws IOException, CustomerAlreadyPresentException {
        List<CustomerModel> list = findAll();
        for(CustomerModel x : list){
            if(x.getId().equalsIgnoreCase(c.getId())){
                throw new CustomerAlreadyPresentException("Customer with this Id : "+c.getId()+" already present");
            }else if(x.getPhone().equalsIgnoreCase(c.getPhone())){
                throw new CustomerAlreadyPresentException("Customer with this Phone : "+c.getPhone()+" already present");
            } else if (x.getEmail().equalsIgnoreCase(c.getEmail())) {
                throw new CustomerAlreadyPresentException("Customer with this Email : "+c.getEmail()+" already present");
            }
        }

        list.add(c);
        ensureHeaderInFile();
        writeAll(list);
    }

    private void writeAll(List<CustomerModel> list) throws IOException{
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        ensureHeaderInFile();
        bw.write(HEADER);
        bw.newLine();
        String line ;
        for(CustomerModel c : list){
            line = c.getId()+","+
                    c.getName()+","+
                    c.getPhone()+","+
                    c.getEmail();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    public List<CustomerModel> findAll() throws IOException {
        List<CustomerModel> list = new ArrayList<CustomerModel>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line=br.readLine()) != null){
            if(!line.trim().isEmpty()){
                CustomerModel c = parseIntoCustomer(line);
                list.add(c);
            }
        }
        return list;
    }
    private CustomerModel parseIntoCustomer(String line){
        CustomerModel c = new CustomerModel();
        String[] word = line.split(",", -1);
        c.setId(word[0]);
        c.setName(word[1]);
        c.setPhone(word[2]);
        c.setEmail(word[3]);

        return c;
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
