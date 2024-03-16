package com.demo.tarefas.services;

import com.demo.tarefas.domain.dto.TaskRequestDTO;
import com.demo.tarefas.domain.dto.TaskResponseDTO;
import com.demo.tarefas.domain.exception.NotFoundException;
import com.demo.tarefas.domain.model.Task;
import com.demo.tarefas.domain.model.User;
import com.demo.tarefas.domain.model.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final RecoveryUserService recoveryUserService;

    @Autowired
    public TaskService(TaskRepository taskRepository, RecoveryUserService recoveryUserService) {
        this.taskRepository = taskRepository;
        this.recoveryUserService = recoveryUserService;
    }

    public List<TaskResponseDTO> get() {

        User user = recoveryUserService.getUser();
        List<Task> list = taskRepository.findByUser(user);

        if (!list.isEmpty()) {

            return list.stream().map(task -> new TaskResponseDTO(task.getId(), task.getTitle(),
                    task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt())).collect(Collectors.toList());

        }
        throw new NotFoundException("No tasks registered");
    }

    @Transactional
    public String store(TaskRequestDTO taskRequestDTO) {

        User user = recoveryUserService.getUser();
        Task task = new Task(taskRequestDTO.title(), taskRequestDTO.description(), LocalDateTime.now(), user);

        taskRepository.save(task);

        return "Task create success";
    }

    @Transactional
    public String update(TaskResponseDTO taskResponseDTO) {

        User user = recoveryUserService.getUser();
        Task task = taskRepository.findByIdAndUser(taskResponseDTO.id(), user);

        if (task != null) {
            task.setTitle(taskResponseDTO.title());
            task.setDescription(taskResponseDTO.description());
            task.setStatus(taskResponseDTO.status());
            task.setUpdatedAt(LocalDateTime.now());

            return "Task update success";
        }
        throw new NotFoundException("Task not found");
    }

    @Transactional
    public String delete(Long id) {

        User user = recoveryUserService.getUser();
        Task task = taskRepository.findByIdAndUser(id, user);

        if (task != null) {
            taskRepository.delete(task);

            return "Task remove success";
        }
        throw new NotFoundException("Task not found");
    }
}
