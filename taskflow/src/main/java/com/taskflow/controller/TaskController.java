package com.taskflow.controller;

import com.taskflow.dto.TaskRequestDTO;
import com.taskflow.dto.TaskResponseDTO;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO dto
    ) {
        log.info("POST /tasks - Creating task");

        TaskResponseDTO response = taskService.createTask(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto
    ) {
        log.info("PUT /tasks/{} - Updating task", id);

        TaskResponseDTO response = taskService.updateTask(id, dto);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.warn("DELETE /tasks/{} - Deleting task", id);

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id) {
        log.debug("GET /tasks/{} - Fetching task", id);

        TaskResponseDTO response = taskService.getTask(id);
        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        log.debug("GET /tasks - Fetching all tasks");

        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
}
