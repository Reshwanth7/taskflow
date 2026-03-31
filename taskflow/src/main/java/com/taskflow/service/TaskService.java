package com.taskflow.service;

import com.taskflow.domain.Task;
import com.taskflow.dto.TaskRequestDTO;
import com.taskflow.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO dto);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO dto);

    void deleteTask(Long id);

    TaskResponseDTO getTask(Long id);

    List<TaskResponseDTO> getAllTasks();
    List<Task> getAllTasksSummary();
    Task getTaskById(Long id);
}

