package com.Rentify.carrental.service;

import com.Rentify.carrental.model.CustomerModel;

import java.util.List;

public interface CustomerService {
    boolean addCustomer(CustomerModel c);
    CustomerModel findById(String id);
    List<CustomerModel> searchByName(String name);
}
