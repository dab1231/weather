package com.nik.weather.exception;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException() {
        super("Failed to fetch weather data");
    }
}
