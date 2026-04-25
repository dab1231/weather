package com.nik.weather.exception;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Invalid login");
    }
}
