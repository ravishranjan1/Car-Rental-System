package com.Rentify.carrental.repo;

import com.Rentify.carrental.exceptions.CarAlreadyPresentException;
import com.Rentify.carrental.exceptions.CarNotFoundException;
import com.Rentify.carrental.model.CarModel;

import java.io.IOException;
import java.util.List;

public interface CarRepo {
    void addCar(CarModel c) throws IOException, CarAlreadyPresentException;
    List<CarModel> findAll()throws IOException;
    void updateCar(CarModel c)throws IOException, CarNotFoundException;
    void removeCar(String id) throws IOException, CarNotFoundException;
}
