package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Tag create/update payload")
public record TagRequestDTO(
        @Schema(description = "Tag name", example = "backend")
        @NotBlank String name,
        @Schema(description = "Hex color for the tag", example = "#3B82F6")
        String color
) {}

