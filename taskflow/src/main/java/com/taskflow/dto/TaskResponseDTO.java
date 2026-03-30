package com.taskflow.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<SubTaskResponseDTO> subTasks,
        List<TagResponseDTO> tags
) {}

