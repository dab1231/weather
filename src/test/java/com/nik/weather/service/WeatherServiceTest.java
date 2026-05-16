package com.nik.weather.service;

import com.nik.weather.config.SpringConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@SpringJUnitConfig(SpringConfig.class)
public class WeatherServiceTest {

    private final HttpClient httpClient;
    private final WeatherService weatherService;

    @Autowired
    public WeatherServiceTest(HttpClient httpClient, WeatherService weatherService) {
        this.httpClient = httpClient;
        this.weatherService = weatherService;
    }

    @Test
    void returnCitiesIfNameIsValid() throws IOException, InterruptedException {
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);
        Mockito.when(response.body()).thenReturn("""
                [{
                    "name": "London",
                    "country": "GB"
                }]
                """);
        Mockito.doReturn(response).when(httpClient).send(Mockito.any(), Mockito.any());

        var citiesRespDtos = weatherService.findCity("London");
        Assertions.assertThat(citiesRespDtos[0].getName()).isEqualTo("London");
    }

    //todo тесты ошибок на статусы
}
