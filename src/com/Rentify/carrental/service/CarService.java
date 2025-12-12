package com.Rentify.carrental.service;

import com.Rentify.carrental.model.CarModel;

import java.util.List;

public interface CarService {
    boolean addCar(CarModel car);
    boolean updateCar(CarModel car);
    boolean removeCar(String id);
    CarModel findCarById(String id);
    List<CarModel> searchBy(String category);
    List<CarModel> searchBy(int seats);

}
