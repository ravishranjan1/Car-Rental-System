package com.Rentify.carrental.handler;

import com.Rentify.carrental.model.CustomerModel;
import com.Rentify.carrental.service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class CustomerHandler {
    private final CustomerService customerService;

    public CustomerHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

     public void handleAddCustomer(Scanner sc){
         System.out.print("Enter Customer Id : ");
         String id = sc.nextLine().trim();

         System.out.print("Enter Name : ");
         String name = sc.nextLine().trim();

         System.out.print("Enter Phone : ");
         String phone = sc.nextLine().trim();

         System.out.print("Enter Gmail : ");
         String gmail = sc.nextLine().trim();

         CustomerModel c = new CustomerModel();
         c.setId(id);
         c.setName(name);
         c.setPhone(phone);
         c.setEmail(gmail);

         boolean res = customerService.addCustomer(c);
         if(res){
             System.out.println("Customer Added Successfully");
         }else{
             System.out.println("Customer Not added");
         }
    }

    public void handleFindById(Scanner sc){
        System.out.print("Enter Id : ");
        String id = sc.nextLine().trim();

        CustomerModel c = customerService.findById(id);
        if(c == null){
            System.out.println("Customer not found with id : "+id);
            return;
        }
        System.out.println();
        System.out.println("ID     | Name                | Phone      | Email                   ");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-6s |%-20s |%-11s |%-20s%n",
                c.getId(),
                c.getName(),
                c.getPhone(),
                c.getEmail()
        );
    }

    public void handleFindByName(Scanner sc){
        System.out.print("Enter Name : ");
        String name = sc.nextLine().trim();

        List<CustomerModel> list = customerService.searchByName(name);
        if(list.isEmpty()){
            System.out.println("Customer Not found with name : "+name);
            return;
        }
        System.out.println();
        System.out.println("ID     | Name                | Phone      | Email                   ");
        System.out.println("----------------------------------------------------------------");
        int i;
        for(i=0;i< list.size();i++){
            CustomerModel c = list.get(i);
            System.out.printf("%-6s |%-20s |%-11s |%-20s%n",
                    c.getId(),
                    c.getName(),
                    c.getPhone(),
                    c.getEmail()
            );
        }
    }


}
