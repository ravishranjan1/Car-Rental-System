package com.Rentify.carrental.repo;

import com.Rentify.carrental.exceptions.CustomerAlreadyPresentException;
import com.Rentify.carrental.model.CustomerModel;

import java.io.IOException;
import java.util.List;

public interface CustomerRepo {
    void addCustomer(CustomerModel c) throws IOException, CustomerAlreadyPresentException;
    public List<CustomerModel> findAll() throws IOException;
}
