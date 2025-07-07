package com.taass.salon_service.exception;

public class SalonNotFoundException extends RuntimeException{
    public SalonNotFoundException(String message) {
        super("Salon not found with id: " + message);
    }
}
