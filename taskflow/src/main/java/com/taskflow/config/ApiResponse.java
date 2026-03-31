package com.taskflow.config;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Standard API wrapper for successful/failed responses")
public record ApiResponse<T>(
        @Schema(description = "Whether request was processed successfully", example = "true")
        boolean success,
        @Schema(description = "Payload object or error payload")
        T data,
        @Schema(description = "Response timestamp in ISO format", example = "2026-03-31T10:15:30")
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(false, data, LocalDateTime.now());
    }
}

