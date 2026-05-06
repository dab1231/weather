package com.nik.weather.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherRespDto {
    private Main main;
    private List<Weather> weather;

    public record Main(double temp,
                       @SerializedName("feels_like") double feelsLike,
                       int humidity) {}

    public record Weather(String icon, String description) {}
}
