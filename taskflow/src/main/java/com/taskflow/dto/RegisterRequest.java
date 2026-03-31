package com.taskflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "User registration payload")
public record RegisterRequest(
		@Schema(description = "Unique username", example = "john")
		@NotBlank String username,
		@Schema(description = "Password for the new account", example = "Pass@123")
		@NotBlank @Size(min = 6, max = 100) String password
) {
}
