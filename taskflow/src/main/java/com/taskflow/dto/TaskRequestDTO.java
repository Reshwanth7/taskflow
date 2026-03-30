package com.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record TaskRequestDTO(
        @NotBlank String title,
        String description,
        @NotNull String status,
        @NotNull String priority,
        LocalDate dueDate,
        List<SubTaskRequestDTO> subTasks,
        List<TagRequestDTO> tags
) {}

