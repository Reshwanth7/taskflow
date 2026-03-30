package com.taskflow.mapper;

import com.taskflow.domain.Subtask;
import com.taskflow.dto.SubTaskRequestDTO;
import com.taskflow.dto.SubTaskResponseDTO;

public class SubTaskMapper {

    public static Subtask toEntity(SubTaskRequestDTO dto) {
        Subtask subTask = new Subtask();
        subTask.setTitle(dto.title());
        subTask.setCompleted(dto.completed());
        return subTask;
    }

    public static SubTaskResponseDTO toDTO(Subtask subTask) {
        return new SubTaskResponseDTO(
                subTask.getId(),
                subTask.getTitle(),
                subTask.isCompleted()
        );
    }
}

