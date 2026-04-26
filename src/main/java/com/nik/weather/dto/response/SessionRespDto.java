package com.nik.weather.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionRespDto(UUID id, LocalDateTime expiresAt) {
}
