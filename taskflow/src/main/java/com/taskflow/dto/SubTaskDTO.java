package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed subtask data")
public record SubTaskDTO(
        @Schema(example = "11")
        Long id,
        @Schema(example = "Create presentation outline")
        String title,
        @Schema(example = "true")
        boolean completed
) {}

