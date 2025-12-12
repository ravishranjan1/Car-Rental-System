package com.Rentify.carrental.repo;

import com.Rentify.carrental.exceptions.CustomerAlreadyPresentException;
import com.Rentify.carrental.model.CustomerModel;

import java.io.IOException;

public interface CustomerRepo {
    void addCustomer(CustomerModel c) throws IOException, CustomerAlreadyPresentException;
}
