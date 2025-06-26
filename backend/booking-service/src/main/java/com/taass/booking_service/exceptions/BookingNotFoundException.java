package com.taass.booking_service.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super("Booking not found with id: " + message);
    }
}
