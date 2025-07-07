package com.taass.salon_service.exception;

public class TimeSlotNotFoundException extends RuntimeException{
    public TimeSlotNotFoundException(String message) {

        super("Time slot not found with id: " + message);
    }
}
