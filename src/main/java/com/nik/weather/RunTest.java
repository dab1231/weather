package com.nik.weather;

import com.google.gson.Gson;
import com.nik.weather.dto.response.CitiesRespDto;
import com.nik.weather.service.WeatherService;

import java.io.IOException;

public class RunTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        WeatherService weatherService = new WeatherService(new Gson());
        var cities = weatherService.findCity("Moscow");
        for (CitiesRespDto citiesRespDto : cities) {
            System.out.println(citiesRespDto.getName() + citiesRespDto.getCountry());
        }
    }
}
