package com.Rentify.carrental.service.impl;

import com.Rentify.carrental.exceptions.CustomerAlreadyPresentException;
import com.Rentify.carrental.model.CustomerModel;
import com.Rentify.carrental.repo.CustomerRepo;
import com.Rentify.carrental.service.CustomerService;

import java.io.IOException;
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
        return null;
    }

    @Override
    public List<CustomerModel> searchByName(String name) {
        return List.of();
    }
}
