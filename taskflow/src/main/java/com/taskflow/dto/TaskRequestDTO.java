package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Task create/update request payload")
public record TaskRequestDTO(
        @Schema(description = "Task title", example = "Prepare sprint demo")
        @NotBlank String title,
        @Schema(description = "Task description", example = "Collect updates and finalize slides")
        String description,
        @Schema(description = "Task status", allowableValues = {"TODO", "IN_PROGRESS", "COMPLETED"}, example = "TODO")
        @NotNull String status,
        @Schema(description = "Task priority", allowableValues = {"LOW", "MEDIUM", "HIGH", "CRITICAL"}, example = "HIGH")
        @NotNull String priority,
        @Schema(description = "Due date in ISO format", example = "2026-04-10")
        LocalDate dueDate,
        @ArraySchema(schema = @Schema(implementation = SubTaskRequestDTO.class))
        List<SubTaskRequestDTO> subTasks,
        @ArraySchema(schema = @Schema(implementation = TagRequestDTO.class))
        List<TagRequestDTO> tags
) {}

