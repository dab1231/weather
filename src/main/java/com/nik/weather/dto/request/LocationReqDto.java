package com.nik.weather.dto.request;

import java.math.BigDecimal;

public record LocationReqDto(String name, BigDecimal latitude, BigDecimal longitude) {
}
