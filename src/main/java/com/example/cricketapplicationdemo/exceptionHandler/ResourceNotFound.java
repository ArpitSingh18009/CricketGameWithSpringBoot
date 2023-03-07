package com.example.cricketapplicationdemo.exceptionHandler;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
