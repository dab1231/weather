package com.nik.weather.dto.response;


import java.math.BigDecimal;

public record CitiesRespDto(String name, BigDecimal latitude, BigDecimal longitude, String country) {
}
