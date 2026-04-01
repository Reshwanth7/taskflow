package com.taskflow.repository;

import com.taskflow.domain.Task;
import com.taskflow.domain.TaskPriority;
import com.taskflow.domain.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveTask() {
        Task task = new Task();
        task.setTitle("DB Test");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(TaskPriority.CRITICAL);

        Task saved = taskRepository.save(task);

        assertNotNull(saved.getId());
    }
}

