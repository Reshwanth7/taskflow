package com.taskflow.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Priority level of a task")
public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
