package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Compact task representation for list pages")
public record TaskSummaryDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Prepare sprint demo")
        String title,
        @Schema(example = "IN_PROGRESS")
        String status,
        @Schema(example = "HIGH")
        String priority,
        @Schema(example = "2026-04-10")
        LocalDate dueDate,
        @Schema(description = "Completion percentage based on subtasks", example = "50")
        int progressPercentage
) {}

