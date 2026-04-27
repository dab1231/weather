package com.nik.weather.exception;

public class UnauthorizedAccessDeniedException extends RuntimeException {
    public UnauthorizedAccessDeniedException() {
        super("Unauthorized access");
    }
}
