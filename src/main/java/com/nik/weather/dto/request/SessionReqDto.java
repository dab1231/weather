package com.nik.weather.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionReqDto(UUID id, LocalDateTime expiresAt) {
}
