package com.example.tasknote.tasknote.exception;

public class ResourceExistedException extends RuntimeException{
    public ResourceExistedException(String message) {
        super(message);
    }
}
