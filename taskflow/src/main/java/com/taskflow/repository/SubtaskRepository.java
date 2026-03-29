package com.taskflow.repository;

import com.taskflow.domain.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
}
