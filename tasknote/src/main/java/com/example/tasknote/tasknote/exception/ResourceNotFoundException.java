package com.example.tasknote.tasknote.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {  
        super(message);
    }
}
