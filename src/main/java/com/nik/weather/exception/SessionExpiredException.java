package com.nik.weather.exception;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException() {
        super("Session was expired");
    }
}
