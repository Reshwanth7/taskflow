package com.taskflow.controller;

import com.taskflow.config.ApiResponse;
import com.taskflow.domain.Task;
import com.taskflow.dto.TaskDetailsDTO;
import com.taskflow.dto.TaskRequestDTO;
import com.taskflow.dto.TaskResponseDTO;
import com.taskflow.dto.TaskSummaryDTO;
import com.taskflow.mapper.TaskMapper;
import com.taskflow.service.TaskService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Task management APIs")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @Operation(summary = "Create a new task", description = "Creates a task with optional subtasks and tags")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Task created",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Task payload", required = true,
                    content = @Content(schema = @Schema(implementation = TaskRequestDTO.class)))
            @Valid @RequestBody TaskRequestDTO dto
    ) {
        log.info("POST /tasks - Creating task");

        TaskResponseDTO response = taskService.createTask(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // UPDATE
    @Operation(summary = "Update task by ID", description = "Updates core task fields, subtasks and tags")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Task updated",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "Task id", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated task payload", required = true,
                    content = @Content(schema = @Schema(implementation = TaskRequestDTO.class)))
            @Valid @RequestBody TaskRequestDTO dto
    ) {
        log.info("PUT /tasks/{} - Updating task", id);

        TaskResponseDTO response = taskService.updateTask(id, dto);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @Operation(summary = "Delete task by ID", description = "Deletes a task and its related subtasks")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Task deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@Parameter(description = "Task id", example = "1") @PathVariable Long id) {
        log.warn("DELETE /tasks/{} - Deleting task", id);

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // GET BY ID
    @Operation(summary = "Get task by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@Parameter(description = "Task id", example = "1") @PathVariable Long id) {
        log.debug("GET /tasks/{} - Fetching task", id);

        TaskResponseDTO response = taskService.getTask(id);
        return ResponseEntity.ok(response);
    }

    // GET ALL
    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tasks retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskResponseDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        log.debug("GET /tasks - Fetching all tasks");

        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get all task summaries")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Task summaries retrieved",
                    content = @Content(schema = @Schema(implementation = TaskSummaryListResponse.class)))
    })
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<TaskSummaryDTO>>> getAllTasksSummary() {
        List<TaskSummaryDTO> tasks = taskService.getAllTasksSummary()
                .stream()
                .map(task-> new TaskMapper().toSummary(task))
                .toList();

        return ResponseEntity.ok(ApiResponse.ok(tasks));
    }


    // GET BY ID
    @Operation(summary = "Get detailed task summary by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Task details retrieved",
                    content = @Content(schema = @Schema(implementation = TaskDetailsResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/summary/{id}")
    public ResponseEntity<ApiResponse<TaskDetailsDTO>> getTaskById(@Parameter(description = "Task id", example = "1") @PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.ok(new TaskMapper().toDetails(task)));
    }

    @Schema(name = "TaskSummaryListResponse", description = "Standard API response with task summary list")
    private record TaskSummaryListResponse(
            @Schema(example = "true") boolean success,
            @ArraySchema(schema = @Schema(implementation = TaskSummaryDTO.class)) List<TaskSummaryDTO> data,
            @Schema(example = "2026-03-31T10:15:30") String timestamp
    ) {}

    @Schema(name = "TaskDetailsResponse", description = "Standard API response with detailed task data")
    private record TaskDetailsResponse(
            @Schema(example = "true") boolean success,
            @Schema(implementation = TaskDetailsDTO.class) TaskDetailsDTO data,
            @Schema(example = "2026-03-31T10:15:30") String timestamp
    ) {}

}
