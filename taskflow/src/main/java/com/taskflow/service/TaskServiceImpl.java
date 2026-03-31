package com.taskflow.service;

import com.taskflow.domain.Tag;
import com.taskflow.domain.Task;
import com.taskflow.dto.TaskRequestDTO;
import com.taskflow.dto.TaskResponseDTO;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.SubTaskMapper;
import com.taskflow.mapper.TagMapper;
import com.taskflow.mapper.TaskMapper;
import com.taskflow.repository.TagRepository;
import com.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);


    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        log.info("Creating new task: {}", dto.title());

        Task task = TaskMapper.toEntity(dto);

        // BUSINESS RULE: Prevent duplicate tags
        if (dto.tags() != null) {
            Set<Tag> finalTags = dto.tags().stream()
                    .map(tagDTO -> tagRepository
                            .findByNameIgnoreCase(tagDTO.name())
                            .orElseGet(() -> tagRepository.save(TagMapper.toEntity(tagDTO)))
                    )
                    .collect(Collectors.toSet());

            task.setTags(finalTags);
        }

        Task saved = taskRepository.save(task);
        return TaskMapper.toDTO(saved);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {

        log.info("Updating task with id: {}", id);


        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update fields
        existing.setTitle(dto.title());
        existing.setDescription(dto.description());
        existing.setStatus(Enum.valueOf(existing.getStatus().getDeclaringClass(), dto.status()));
        existing.setPriority(Enum.valueOf(existing.getPriority().getDeclaringClass(), dto.priority()));
        existing.setDueDate(dto.dueDate());

        // BUSINESS RULE: Replace subtasks
        existing.getSubtasks().clear();
        if (dto.subTasks() != null) {
            dto.subTasks().forEach(st -> {
                var subTask = SubTaskMapper.toEntity(st);
                subTask.setTask(existing);
                existing.getSubtasks().add(subTask);
            });
        }

        // BUSINESS RULE: Prevent duplicate tags
        if (dto.tags() != null) {
            Set<Tag> finalTags = dto.tags().stream()
                    .map(tagDTO -> tagRepository
                            .findByNameIgnoreCase(tagDTO.name())
                            .orElseGet(() -> tagRepository.save(TagMapper.toEntity(tagDTO)))
                    )
                    .collect(Collectors.toSet());

            existing.setTags(finalTags);
        }

        Task saved = taskRepository.save(existing);
        return TaskMapper.toDTO(saved);
    }

    @Override
    public void deleteTask(Long id) {

        log.warn("Deleting task with id: {}", id);

        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDTO getTask(Long id) {

        log.debug("Fetching task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return TaskMapper.toDTO(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {

        log.debug("Fetching all tasks");

        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    @Override
    public List<Task> getAllTasksSummary() {

        log.debug("Fetching all tasks Summary");

        return taskRepository.findAll();
    }

   @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

}

