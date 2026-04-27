package com.nik.weather.exception;

public class UserNotFoundExcpetion extends RuntimeException {
    public UserNotFoundExcpetion() {
        super("User not found");
    }
}
