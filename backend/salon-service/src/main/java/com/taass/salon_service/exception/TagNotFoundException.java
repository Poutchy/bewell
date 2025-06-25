package com.taass.salon_service.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(String message) {

        super("Tag not found with id: " + message);
    }
}
