package com.Rentify.carrental;

import com.Rentify.carrental.handler.CarHandler;
import com.Rentify.carrental.handler.CustomerHandler;
import com.Rentify.carrental.handler.RentalRecordHandler;

import java.util.Scanner;

public class AppRunner {
    private final CustomerHandler customerHandler;
    private final CarHandler carHandler;
    private final RentalRecordHandler recordHandler;

    public AppRunner(CustomerHandler customerHandler, CarHandler carHandler, RentalRecordHandler recordHandler) {
        this.customerHandler = customerHandler;
        this.carHandler = carHandler;
        this.recordHandler = recordHandler;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Rentify CarRental");
        printHelp();
        boolean running = true;
        while(running){
            System.out.println("\n> ");
            String command = sc.nextLine().trim();
            try{
                String normalizeCommand = command.toLowerCase().trim();
                switch(normalizeCommand){
                    case "help" -> printHelp();
                    case "register-customer" -> customerHandler.handleAddCustomer(sc);
                    case "find-customer"  -> customerHandler.handleFindById(sc);
                    case "list-customers"  -> customerHandler.handleFindByName(sc);
                    case "add-car"  ->  carHandler.handleAddCar(sc);
                    case "remove-car"  -> carHandler.handleRemoveCar(sc);
                    case "find-carbyid"  -> carHandler.handleFindCarById(sc);
                    case "list-car"  -> carHandler.handleSearchByCategory(sc);
                    case "list-carbyseat"  ->  carHandler.handleSearchBySeats(sc);
                    case "rent-car"  -> recordHandler.handleRentCar(sc);
                    case "return-car"  -> recordHandler.returnCar(sc);
                    case "list-ongoing"  -> recordHandler.handleListOngoing();
                    case "history-customer"  -> recordHandler.handleHistoryOfCustomer(sc);
                    case "exit","quit"   -> running = false;
                    default ->{
                        System.out.println("Unknown command. Type 'help' for list.");
                    }
                }
            }catch(Exception e){
                System.out.println("Unexpected Error "+e.getMessage());
            }
        }

    }

    public void printHelp(){
        System.out.println("commands: ");
        System.out.println("  register-customer     - add a new customer");
        System.out.println("  find-customer         - find Customer by Id");
        System.out.println("  list-customers        - list of Customer by Name");
        System.out.println("  add-car               - add a new Car");
        System.out.println("  remove-car            - remove car");
        System.out.println("  find-carById          - find Car by id");
        System.out.println("  list-car              - list of car by category");
        System.out.println("  list-carBySeat        - list of car by number of seats");
        System.out.println("  rent-car              - rent a car");
        System.out.println("  return-car            - return the rented car");
        System.out.println("  list-ongoing          - list of car whose STATUS is ONGOING");
        System.out.println("  history-customer      - rental history of a customer");
        System.out.println("  help                  - show this help");
        System.out.println("  exit / quit           - exit application");
    }


}
