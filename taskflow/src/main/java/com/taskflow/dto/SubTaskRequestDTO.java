package com.taskflow.dto;

import jakarta.validation.constraints.NotBlank;

public record SubTaskRequestDTO(
        @NotBlank String title,
        boolean completed
) {}

