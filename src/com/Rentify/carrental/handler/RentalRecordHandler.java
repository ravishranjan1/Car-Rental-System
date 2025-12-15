package com.Rentify.carrental.handler;

import com.Rentify.carrental.exceptions.CarNotAvailableException;
import com.Rentify.carrental.model.CarModel;
import com.Rentify.carrental.model.CustomerModel;
import com.Rentify.carrental.model.RentalRecordModel;
import com.Rentify.carrental.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class RentalRecordHandler {
    private final CarService carService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private static final Logger log =  Logger.getLogger(RentalRecordHandler.class.getName());

    public RentalRecordHandler(CarService carService, CustomerService customerService, RentalService rentalService) {
        this.carService = carService;
        this.customerService = customerService;
        this.rentalService = rentalService;
    }

    public void handleRentCar(Scanner sc){
        System.out.print("Enter Car Id : ");
        String carId = sc.nextLine().trim();

        CarModel c = carService.findCarById(carId);
        if(c!=null){
            try{
                if(!c.isAvailable()){
                    throw new CarNotAvailableException("Car is not available for rent");
                }

                System.out.print("Enter Customer Id : ");
                String customerId = sc.nextLine().trim();

                CustomerModel cus = customerService.findById(customerId);
                if(cus!=null){
                    System.out.print("Enter Start Date(yyyy-MM-dd) : ");
                    String startDate = sc.nextLine();
                    LocalDate start = LocalDate.parse(startDate);

                    System.out.print("Enter Due Date(yyyy-MM-dd) : ");
                    String dueDate = sc.nextLine();
                    LocalDate due = LocalDate.parse(dueDate);

                    RentalRecordModel res = rentalService.rentCar(carId, customerId, start, due);
                    if(res != null){
                        System.out.println("Rent the car with id : "+carId+" successful");
                        System.out.println("id     | carId   | customerId | startDate  | dueDate    | status");
                        System.out.println("------------------------------------------------");
                        System.out.printf(
                                "%-6s | %-7s | %-10s | %-10s | %-10s | %-8s%n",
                                res.getId(),
                                res.getCarId(),
                                res.getCustomerId(),
                                res.getStartDate(),
                                res.getDueDate(),
                                res.getStatus()
                        );
                    }else{
                        System.out.println("Car is not rented");
                    }
                }else{
                    System.out.println("Customer not found with id : "+customerId);
                }
            } catch (CarNotAvailableException e) {
                log.severe("CarNotAvailableException occurred, "+e.getMessage());
                System.out.println("Car is not available");
            }
        }else{
            System.out.println("Car not found with id : "+carId);
        }
    }

    public void returnCar(Scanner sc){
        System.out.print("Enter rental Id : ");
        String rentalId = sc.nextLine().trim();

        if(rentalService.isRentalIdPresent(rentalId)){
            System.out.print("Enter return Date (yyyy-MM-dd) : ");
            String returnDateStr = sc.nextLine().trim();

            if (returnDateStr.isEmpty()) {
                System.out.println("Return date cannot be empty");
                return;
            }

            LocalDate returnDate;
            try {
                returnDate = LocalDate.parse(returnDateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd");
                return;
            }

            RentalRecordModel res= rentalService.returnCar(rentalId, returnDate);
            if(res != null){
                System.out.println("Car is returned successfully");
                System.out.println("id     | carId   | customerId | startDate  | dueDate    | returnDate | totalCost | status");
                System.out.println("--------------------------------------------------------");
                System.out.printf(
                        "%-6s | %-7s | %-10s | %-10s | %-10s | %-10s | %-9.2f | %-8s%n",
                        res.getId(),
                        res.getCarId(),
                        res.getCustomerId(),
                        res.getStartDate(),
                        res.getDueDate(),
                        res.getReturnDate(),
                        res.getTotalCost(),
                        res.getStatus()
                );
            }else{
                System.out.println("Car is not returned");
            }
        }else{
            System.out.println("Rental Id is not matched");
        }
    }

    public void handleListOngoing(){
        List<RentalRecordModel> list = rentalService.listOngoingRentals();
        if(list.isEmpty()){
            System.out.println("No Car found in ONGOING Status");
            return;
        }
        System.out.println("id     | carId   | customerId | startDate  | dueDate    | status");
        System.out.println("----------------------------------------------------------------");
        int i;
        for(i=0;i< list.size();i++){
            RentalRecordModel res = list.get(i);
            System.out.printf(
                    "%-6s | %-7s | %-10s | %-10s | %-10s | %-8s%n",
                    res.getId(),
                    res.getCarId(),
                    res.getCustomerId(),
                    res.getStartDate(),
                    res.getDueDate(),
                    res.getStatus()
            );
        }
    }

    public void handleHistoryOfCustomer(Scanner sc){
        System.out.print("Enter Customer Id : ");
        String customerId = sc.nextLine().trim();

        CustomerModel cus = customerService.findById(customerId);
        if(cus != null){
            List<RentalRecordModel> list = rentalService.getRentalHistoryForCustomer(customerId);
            if(list.isEmpty()){
                System.out.println("No Car found in ONGOING Status");
                return;
            }
            System.out.println("id     | carId   | customerId | startDate  | dueDate    | returnDate | totalCost | status");
            System.out.println("------------------------------------------------------------");
            int i;
            for(i=0;i< list.size();i++){
                RentalRecordModel res = list.get(i);
                System.out.printf(
                        "%-6s | %-7s | %-10s | %-10s | %-10s | %-10s | %-9.2f | %-8s%n",
                        res.getId(),
                        res.getCarId(),
                        res.getCustomerId(),
                        res.getStartDate(),
                        res.getDueDate(),
                        res.getReturnDate(),
                        res.getTotalCost(),
                        res.getStatus()
                );
            }
        }else{
            System.out.println("Customer not found with id : "+customerId);
        }
    }


}
