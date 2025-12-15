package com.Rentify.carrental.service;

import com.Rentify.carrental.model.RentalRecordModel;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    RentalRecordModel rentCar(String carId, String customerId, LocalDate start, LocalDate due);
    RentalRecordModel returnCar(String rentalId, LocalDate returnDate);
    boolean isRentalIdPresent(String rentalId);
    List<RentalRecordModel> listOngoingRentals();
    List<RentalRecordModel> getRentalHistoryForCustomer(String customerId);
}
