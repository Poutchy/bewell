package com.taass.salon_service.exception;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message) {
        super("Service not found with id: " + message);
    }
}
