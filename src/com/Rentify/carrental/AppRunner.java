package com.Rentify.carrental;

import com.Rentify.carrental.handler.CustomerHandler;

import java.util.Scanner;

public class AppRunner {
    private final CustomerHandler customerHandler;

    public AppRunner(CustomerHandler customerHandler) {
        this.customerHandler = customerHandler;
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

                    case "exit","quit"   -> running = false;
                }
            }catch(Exception e){
                System.out.println("Unexpected Error "+e.getMessage());
            }
        }

    }

    public void printHelp(){
        System.out.println("commands: ");
        System.out.println("register-customer       - add a new customer");

        System.out.println("  help                  - show this help");
        System.out.println("  exit / quit           - exit application");
    }


}
