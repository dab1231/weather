package com.nik.weather.dto.response;

import java.math.BigDecimal;

public record LocationRespDto(String name, BigDecimal latitude, BigDecimal longitude) {
}