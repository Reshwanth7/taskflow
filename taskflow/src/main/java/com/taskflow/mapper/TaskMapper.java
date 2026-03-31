package com.taskflow.mapper;

import com.taskflow.domain.Subtask;
import com.taskflow.domain.Task;
import com.taskflow.domain.TaskPriority;
import com.taskflow.domain.TaskStatus;
import com.taskflow.dto.*;

import java.util.List;
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


    public TaskSummaryDTO toSummary(Task task) {
        int total = task.getSubtasks().size();
        int completed = (int) task.getSubtasks().stream().filter(Subtask::isCompleted).count();
        int progress = total == 0 ? 0 : (completed * 100) / total;

        return new TaskSummaryDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate(),
                progress
        );
    }

    public TaskDetailsDTO toDetails(Task task) {
        int total = task.getSubtasks().size();
        int completed = (int) task.getSubtasks().stream().filter(Subtask::isCompleted).count();
        int progress = total == 0 ? 0 : (completed * 100) / total;

        List<SubTaskDTO> subTaskDTOs = task.getSubtasks().stream()
                .map(st -> new SubTaskDTO(st.getId(), st.getTitle(), st.isCompleted()))
                .toList();

        return new TaskDetailsDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                total,
                completed,
                progress,
                subTaskDTOs
        );
    }
}

