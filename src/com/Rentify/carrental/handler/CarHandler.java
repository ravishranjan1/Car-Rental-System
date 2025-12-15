package com.Rentify.carrental.handler;

import com.Rentify.carrental.model.CarModel;
import com.Rentify.carrental.service.CarService;

import java.util.List;
import java.util.Scanner;

public class CarHandler {
    private final CarService carService;

    public CarHandler(CarService carService) {
        this.carService = carService;
    }

    public void handleAddCar(Scanner sc){
        System.out.print("Enter Id : ");
        String id = sc.nextLine().trim();

        System.out.print("Enter Make : ");
        String make = sc.nextLine().trim();

        System.out.print("Enter Model : ");
        String model = sc.nextLine().trim();

        System.out.print("Enter Category : ");
        String category = sc.nextLine().trim();

        System.out.print("Enter Seats : ");
        int seats = sc.nextInt();

        System.out.print("Enter DailyRate : ");
        double dailyRate = sc.nextDouble();

        boolean available = true;

        CarModel c = new CarModel();
        c.setId(id);
        c.setMake(make);
        c.setModel(model);
        c.setCategory(category);
        c.setSeats(seats);
        c.setDailyRate(dailyRate);
        c.setAvailable(available);

        boolean res = carService.addCar(c);
        if(res){
            System.out.println("Car added successfully");
        }else{
            System.out.println("Car not added");
        }
    }

    public void handleRemoveCar(Scanner sc){
        System.out.print("Enter Car Id : ");
        String id = sc.nextLine().trim();

        boolean res = carService.removeCar(id);
        if(res){
            System.out.println("Car removed successfully");
        }else{
            System.out.println("Car not removed");
        }
    }

    public void handleFindCarById(Scanner sc){
        System.out.print("Enter Car Id : ");
        String id = sc.nextLine().trim();

        CarModel c = carService.findCarById(id);
        if(c== null){
            System.out.println("Car Not found with this Id "+id);
            return;
        }

        System.out.println();
        System.out.println("id     | make      | model      | category | seats | dailyRate | available");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-6s | %-9s | %-10s | %-8s | %-5s | %-10s | %-1s",
                c.getId(),
                c.getMake(),
                c.getModel(),
                c.getCategory(),
                c.getSeats(),
                c.getDailyRate(),
                c.isAvailable()
        );
    }

    public void handleSearchByCategory(Scanner sc){
        System.out.print("Enter Category of the Car : ");
        String category = sc.nextLine().trim();

        List<CarModel> list = carService.searchBy(category);
        if(list.isEmpty()){
            System.out.println("Car not found by this category : "+category);
            return;
        }

        System.out.println();
        System.out.println("id     | make      | model      | category | seats | dailyRate | available");
        System.out.println("---------------------------------------------------------------");
        int i;
        for(i=0; i<list.size();i++){
            CarModel c = list.get(i);
            System.out.printf("%-6s | %-9s | %-10s | %-8s | %-5d | %-10.2f | %-9s%n",
                    c.getId(),
                    c.getMake(),
                    c.getModel(),
                    c.getCategory(),
                    c.getSeats(),
                    c.getDailyRate(),
                    c.isAvailable()
            );
        }
    }

    public void handleSearchBySeats(Scanner sc){
        System.out.print("Enter No. of seats in Car : ");
        int seats = sc.nextInt();

        List<CarModel> list = carService.searchBy(seats);
        if(list.isEmpty()){
            System.out.println("Car not found by this number of seats : "+seats);
            return;
        }

        System.out.println();
        System.out.println("id     | make      | model      | category | seats | dailyRate | available");
        System.out.println("---------------------------------------------------------------");
        int i;
        for(i=0; i<list.size();i++){
            CarModel c = list.get(i);
            System.out.printf(
                    "%-6s | %-9s | %-10s | %-8s | %-5d | %-10.2f | %-9s%n",
                    c.getId(),
                    c.getMake(),
                    c.getModel(),
                    c.getCategory(),
                    c.getSeats(),
                    c.getDailyRate(),
                    c.isAvailable()
            );
        }
    }

}
