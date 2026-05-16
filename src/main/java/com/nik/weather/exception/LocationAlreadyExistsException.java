package com.nik.weather.exception;

public class LocationAlreadyExistsException extends RuntimeException {
    public LocationAlreadyExistsException() {
        super("This location already exists");
    }
}
