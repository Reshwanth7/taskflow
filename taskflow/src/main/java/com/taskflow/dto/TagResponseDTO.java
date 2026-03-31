package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tag response payload")
public record TagResponseDTO(
        @Schema(example = "5")
        Long id,
        @Schema(example = "backend")
        String name,
        @Schema(example = "#3B82F6")
        String color
) {}

