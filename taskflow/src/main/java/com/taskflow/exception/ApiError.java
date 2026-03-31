package com.taskflow.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Structured error payload")
public record ApiError(
        @Schema(example = "2026-03-31T10:15:30")
        LocalDateTime timestamp,
        @Schema(example = "400")
        int status,
        @Schema(example = "BAD_REQUEST")
        String error,
        @Schema(example = "Task title must not be blank")
        String message,
        @Schema(example = "/tasks")
        String path
) {}

