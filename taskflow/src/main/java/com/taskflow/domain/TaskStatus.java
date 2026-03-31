package com.taskflow.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Current lifecycle state of a task")
public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    COMPLETED
}
