package com.taskflow.dto;

public record SubTaskResponseDTO(
        Long id,
        String title,
        boolean completed
) {}

