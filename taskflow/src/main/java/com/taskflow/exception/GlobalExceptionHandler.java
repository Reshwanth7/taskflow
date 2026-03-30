package com.taskflow.exception;

import com.taskflow.controller.TaskController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private ApiError buildError(HttpStatus status, String message, HttpServletRequest request) {
        return new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.name(),
                message,
                request.getRequestURI()
        );
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Resource not found: {}", ex.getMessage());

        ApiError error = buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        log.error("Bad request: {}", ex.getMessage());

        ApiError error = buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Validation errors (DTO @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        log.error("Validation failed: {}", message);


        ApiError error = buildError(HttpStatus.BAD_REQUEST, message, request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Constraint violations (path variables, request params)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
        ApiError error = buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);
        ApiError error = buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

