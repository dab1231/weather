package com.nik.weather.service;

import com.google.gson.Gson;
import com.nik.weather.dto.response.CitiesRespDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

//    @Value("${openweather.api.key}")
    private String apiKey;
    private final Gson gson;

    public WeatherService(Gson gson, String apiKey) {
        this.gson = gson;
        this.apiKey = apiKey;
    }

    public CitiesRespDto[] findCity(String cityName) throws IOException, InterruptedException {

        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + apiKey)).build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        var respString = response.body();

        var citiesRespDto = gson.fromJson(respString, CitiesRespDto[].class);
        return citiesRespDto;
    }
}
