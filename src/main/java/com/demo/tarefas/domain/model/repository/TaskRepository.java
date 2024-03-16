package com.demo.tarefas.domain.model.repository;

import com.demo.tarefas.domain.model.Task;
import com.demo.tarefas.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByUser(User user);

    public Task findByIdAndUser(Long id,User user);

}
