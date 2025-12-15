package com.Rentify.carrental.service.impl;

import com.Rentify.carrental.exceptions.CustomerAlreadyPresentException;
import com.Rentify.carrental.exceptions.CustomerNotFoundException;
import com.Rentify.carrental.model.CustomerModel;
import com.Rentify.carrental.repo.CustomerRepo;
import com.Rentify.carrental.service.CustomerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private static final Logger log =  Logger.getLogger(CustomerServiceImpl.class.getName());

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public boolean addCustomer(CustomerModel c) {

        try {
            customerRepo.addCustomer(c);
            log.info("Customer added");
            return true;
        } catch (IOException e) {
            log.severe("IOException occurred, Customer not added "+e.getMessage());
        } catch(CustomerAlreadyPresentException e){
            log.severe("CustomerAlreadyException occurred, Customer not added "+e.getMessage());
        }
        return false;
    }

    @Override
    public CustomerModel findById(String id) {
        CustomerModel c = null;
        try {
            List<CustomerModel> list = customerRepo.findAll();
            for(CustomerModel x : list){
                if(x.getId().equalsIgnoreCase(id)){
                    log.info("Customer Found with id : "+id);
                    c = x;
                    break;
                }
            }
            if(c == null) {
                throw new CustomerNotFoundException("Customer with this Id : " + id + " is not found");
            }
        } catch (IOException e) {
            log.severe("IOException occurred, Customer Not found with Id : "+id+e.getMessage());
        }catch (CustomerNotFoundException e) {
            log.severe("CustomerNotFoundException occurred, Customer Not found with Id : "+id+e.getMessage());
        }

        return c;
    }

    @Override
    public List<CustomerModel> searchByName(String name) {
        List<CustomerModel> res = new ArrayList<>();
        try {
            List<CustomerModel> list = customerRepo.findAll();
            for(CustomerModel c : list){
                if(c.getName().equalsIgnoreCase(name)){
                    res.add(c);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, Customer Not found with name : "+name+e.getMessage());
        }
        if(res.isEmpty()){
            try {
                throw new CustomerNotFoundException("Customer with this name : "+name+" is not found");
            } catch (CustomerNotFoundException e) {
                log.severe("CustomerNotFoundException occurred, Customer Not found with name : "+name+e.getMessage());
            }
        }
        return res;

    }
}
