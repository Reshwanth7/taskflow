package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Authentication request payload")
public record AuthRequest(
		@Schema(description = "Username used for login", example = "john")
		@NotBlank String username,
		@Schema(description = "Plain text password", example = "Pass@123")
		@NotBlank String password
) {
}
