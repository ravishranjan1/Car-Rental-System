package com.Rentify.carrental.exceptions;

public class CarNotAvailableException extends RuntimeException{
    public CarNotAvailableException(String message) {
        super(message);
    }
}
