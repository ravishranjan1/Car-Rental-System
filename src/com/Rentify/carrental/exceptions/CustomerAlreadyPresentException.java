package com.Rentify.carrental.exceptions;

public class CustomerAlreadyPresentException extends RuntimeException {
    public CustomerAlreadyPresentException(String message) {
        super(message);
    }
}
