package com.artem.taskapi.repository;

import com.artem.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOwnerId(Long id);

}
