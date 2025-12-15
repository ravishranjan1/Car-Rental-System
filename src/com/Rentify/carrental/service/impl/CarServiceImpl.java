package com.Rentify.carrental.service.impl;

import com.Rentify.carrental.exceptions.CarAlreadyPresentException;
import com.Rentify.carrental.exceptions.CarNotFoundException;
import com.Rentify.carrental.model.CarModel;
import com.Rentify.carrental.repo.CarRepo;
import com.Rentify.carrental.service.CarService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CarServiceImpl implements CarService {
    private final CarRepo carRepo;
    private static final Logger log =  Logger.getLogger(CarServiceImpl.class.getName());

    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public boolean addCar(CarModel car) {
        try {
            carRepo.addCar(car);
            log.info("Car added successfully");
            return true;
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        } catch (CarAlreadyPresentException e){
            log.severe("CarAlreadyPresentException occurred, "+e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateCar(CarModel car) {
        try {
            carRepo.updateCar(car);
            log.info("Car updated successfully");
            return true;
        } catch (IOException e) {
            log.severe("IOException occurred, Car Not Updated "+e.getMessage());
        } catch (CarNotFoundException e) {
            log.severe("CarNotFoundException occurred, Car Not updated "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeCar(String id) {
        try {
            carRepo.removeCar(id);
            log.info("Car removed successfully");
            return true;
        } catch (IOException e) {
            log.severe("IOException occurred, Car Not Updated "+e.getMessage());
        } catch (CarNotFoundException e) {
            log.severe("CarNotFoundException occurred, Car Not updated "+e.getMessage());
        }

        return false;
    }

    @Override
    public CarModel findCarById(String id) {
        CarModel c = null;
        try {
            List<CarModel> list = carRepo.findAll();
            for(CarModel x : list){
                if(x.getId().equalsIgnoreCase(id)){
                    c = x;
                    break;
                }
            }
            if(c == null){
                    throw new CarNotFoundException("Car with this id : "+id+" is not found. ");
            }
        } catch (IOException e) {
            log.severe("IOException occurred, Customer Not found with Id : "+id+e.getMessage());
        }catch (CarNotFoundException e) {
            log.severe("CarNotFoundException occurred, "+e.getMessage());
        }
        return c;
    }

    @Override
    public List<CarModel> searchBy(String category) {
        List<CarModel> res = new ArrayList<>();

        try {
            List<CarModel> list = carRepo.findAll();
            for(CarModel c : list){
                if(c.getCategory().equalsIgnoreCase(category)){
                    res.add(c);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return res;
    }

    @Override
    public List<CarModel> searchBy(int seats) {
        List<CarModel> res = new ArrayList<>();
        try {
            List<CarModel> list = carRepo.findAll();
            for(CarModel c : list){
                if(c.getSeats()==seats){
                    res.add(c);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred "+e.getMessage());
        }
        return res;
    }
}
