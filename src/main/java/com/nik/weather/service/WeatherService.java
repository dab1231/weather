package com.nik.weather.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nik.weather.dto.response.CitiesRespDto;
import com.nik.weather.exception.WeatherApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;
    private final Gson gson;

    @Autowired
    public WeatherService(Gson gson) {
        this.gson = gson;
    }

    public CitiesRespDto[] findCity(String cityName) {

        try {
            var httpClient = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(
                    URI.create("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + apiKey)).build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var respString = response.body();

            return gson.fromJson(respString, CitiesRespDto[].class);

        } catch (IOException | InterruptedException | JsonSyntaxException e) {
            throw new WeatherApiException();
        }
    }
}
