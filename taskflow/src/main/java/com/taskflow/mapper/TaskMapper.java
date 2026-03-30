package com.taskflow.mapper;

import com.taskflow.domain.Task;
import com.taskflow.domain.TaskPriority;
import com.taskflow.domain.TaskStatus;
import com.taskflow.dto.TaskRequestDTO;
import com.taskflow.dto.TaskResponseDTO;

import java.util.stream.Collectors;

public class TaskMapper {

    public static Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(TaskStatus.valueOf(dto.status()));
        task.setPriority(TaskPriority.valueOf(dto.priority()));
        task.setDueDate(dto.dueDate());

        // SubTasks
        if (dto.subTasks() != null) {
            var subTasks = dto.subTasks().stream()
                    .map(SubTaskMapper::toEntity)
                    .peek(st -> st.setTask(task))
                    .collect(Collectors.toList());

            task.setSubtasks(subTasks);
        }

        // Tags
        if (dto.tags() != null) {
            var tags = dto.tags().stream()
                    .map(TagMapper::toEntity)
                    .collect(Collectors.toSet());

            task.setTags(tags);
        }

        return task;
    }

    public static TaskResponseDTO toDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getSubtasks().stream()
                        .map(SubTaskMapper::toDTO)
                        .collect(Collectors.toList()),
                task.getTags().stream()
                        .map(TagMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }
}

