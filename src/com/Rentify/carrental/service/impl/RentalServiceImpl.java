package com.Rentify.carrental.service.impl;

import com.Rentify.carrental.exceptions.RentalException;
import com.Rentify.carrental.handler.RentalRecordHandler;
import com.Rentify.carrental.model.CarModel;
import com.Rentify.carrental.model.RentalRecordModel;

import com.Rentify.carrental.model.RentalStatus;
import com.Rentify.carrental.repo.RentalRecordRepo;
import com.Rentify.carrental.service.CarService;
import com.Rentify.carrental.service.RentalService;
import com.Rentify.carrental.utiil.DateUtil;
import com.Rentify.carrental.utiil.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RentalServiceImpl implements RentalService {
    private final RentalRecordRepo recordRepo;
    private final CarService carService;
    private static final Logger log =  Logger.getLogger(RentalServiceImpl.class.getName());

    public RentalServiceImpl(RentalRecordRepo recordRepo, CarService carService) {
        this.recordRepo = recordRepo;
        this.carService = carService;
    }

    @Override
    public RentalRecordModel rentCar(String carId, String customerId, LocalDate start, LocalDate due) {
        RentalRecordModel r = null;
        try{
            if (start == null || due == null){
                throw new RentalException("Dates required");
            }
            if (due.isBefore(start)) {
                throw new RentalException("Due date must be on/after start");
            }
            r = new RentalRecordModel();
            r.setCarId(carId);
            r.setCustomerId(customerId);
            r.setId(IdGenerator.recordId());
            r.setStatus(RentalStatus.ONGOING);
            r.setStartDate(start);
            r.setDueDate(due);

            recordRepo.saveRecord(r);
            log.info("Rent the car successfully");
            CarModel c = carService.findCarById(carId);
            c.setAvailable(false);
            carService.updateCar(c);
        }catch(IOException e){
            log.severe("IOException Occurred,"+ e.getMessage());
        }catch(RentalException e){
            log.severe("RentalException occurred, "+e.getMessage());
        }
        return r;
    }

    @Override
    public RentalRecordModel returnCar(String rentalId, LocalDate returnDate) {
        RentalRecordModel r = null;
        try {
            if(returnDate == null){
                throw new RentalException("Return date requires");
            }

            List<RentalRecordModel> list = recordRepo.findAll();
            for(RentalRecordModel x : list){
                r=x;
                if(r.getId().equalsIgnoreCase(rentalId)){
                    if(r.getStatus()==RentalStatus.RETURNED){
                        throw new RentalException("Car Already returned");
                    }
                    r.setReturnDate(returnDate);
                    r.setStatus(RentalStatus.RETURNED);
                    String carId = r.getCarId();
                    CarModel c = carService.findCarById(carId);
                    double dailyRate = c.getDailyRate();
                    int daysRented = DateUtil.daysRented(r.getStartDate(), r.getDueDate());
                    double baseCost = daysRented*dailyRate;
                    int lateDays = DateUtil.daysRented(r.getDueDate(), returnDate);
                    double lateFee = lateDays*(dailyRate*1.2);
                    double totalCost = baseCost+lateFee;
                    r.setTotalCost(totalCost);

                    recordRepo.updateRecord(r);
                    c.setAvailable(true);
                    carService.updateCar(c);
                }
            }
        } catch (RentalException e) {
            log.severe("RentalException occurred, "+e.getMessage());
        } catch (IOException e) {
            log.severe("IOException Occurred,"+ e.getMessage());
        }

        return r;
    }

    public boolean isRentalIdPresent(String rentalId){
        try {
            List<RentalRecordModel> list = recordRepo.findAll();
            for(RentalRecordModel r : list){
                if(r.getId().equalsIgnoreCase(rentalId)){
                    return true;
                }
            }
        } catch (IOException e) {
            log.severe("IOException occured, "+e.getMessage());
        }
        return false;
    }


    @Override
    public List<RentalRecordModel> listOngoingRentals() {
        List<RentalRecordModel> res = null;
        try {
            res = new ArrayList<>();
            List<RentalRecordModel> list = recordRepo.findAll();
            for(RentalRecordModel r : list){
                if(r.getStatus()==RentalStatus.ONGOING){
                    res.add(r);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return res;
    }

    @Override
    public List<RentalRecordModel> getRentalHistoryForCustomer(String customerId) {
        List<RentalRecordModel> res = null;

        try {
            res = new ArrayList<>();
            List<RentalRecordModel> list = recordRepo.findAll();
            for(RentalRecordModel r : list){
                if(r.getCustomerId().equalsIgnoreCase(customerId)){
                    res.add(r);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return res;
    }
}
