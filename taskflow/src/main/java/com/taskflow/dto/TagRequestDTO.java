package com.taskflow.dto;

import jakarta.validation.constraints.NotBlank;

public record TagRequestDTO(
        @NotBlank String name,
        String color
) {}

