package com.nik.weather.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nik.weather.dto.response.CitiesRespDto;
import com.nik.weather.dto.response.WeatherRespDto;
import com.nik.weather.exception.WeatherApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;
    private final Gson gson;
    private final HttpClient httpClient;

    @Autowired
    public WeatherService(Gson gson, HttpClient httpClient) {
        this.gson = gson;
        this.httpClient = httpClient;
    }

    public CitiesRespDto[] findCity(String cityName) {

        try {
            cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
            var request = HttpRequest.newBuilder(
                    URI.create("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + apiKey)).build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() != 200) {
                throw new WeatherApiException();
            }

            var respString = response.body();
            return gson.fromJson(respString, CitiesRespDto[].class);

        } catch (IOException | InterruptedException | JsonSyntaxException e) {
            throw new WeatherApiException();
        }
    }

    public WeatherRespDto getWeather(BigDecimal lat, BigDecimal lon) {

        try {
            var httpRequest = HttpRequest.newBuilder(
                    URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric")
            ).build();

            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new WeatherApiException();
            }

            var respString = response.body();
            return gson.fromJson(respString, WeatherRespDto.class);
        } catch (IOException | InterruptedException | JsonSyntaxException e) {
            throw new WeatherApiException();
        }
    }
}
