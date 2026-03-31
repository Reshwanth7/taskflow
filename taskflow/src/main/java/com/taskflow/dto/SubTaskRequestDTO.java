package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Subtask create/update payload")
public record SubTaskRequestDTO(
        @Schema(example = "Create presentation outline")
        @NotBlank String title,
        @Schema(example = "false")
        boolean completed
) {}

