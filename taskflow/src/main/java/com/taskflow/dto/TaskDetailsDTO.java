package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Detailed task view with progress metrics")
public record TaskDetailsDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Prepare sprint demo")
        String title,
        @Schema(example = "Collect updates and finalize slides")
        String description,
        @Schema(example = "IN_PROGRESS")
        String status,
        @Schema(example = "HIGH")
        String priority,
        @Schema(example = "2026-04-10")
        LocalDate dueDate,
        @Schema(example = "2026-03-31T10:15:30")
        LocalDateTime createdAt,
        @Schema(example = "2026-03-31T12:20:45")
        LocalDateTime updatedAt,
        @Schema(example = "4")
        int totalSubTasks,
        @Schema(example = "2")
        int completedSubTasks,
        @Schema(example = "50")
        int progressPercentage,
        @ArraySchema(schema = @Schema(implementation = SubTaskDTO.class))
        List<SubTaskDTO> subTasks
) {}

