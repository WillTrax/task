package com.demo.tarefas.controllers;

import com.demo.tarefas.domain.dto.TaskRequestDTO;
import com.demo.tarefas.domain.dto.TaskResponseDTO;
import com.demo.tarefas.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(description = "Task CRUD Routes", name = "Task Controller")
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks")
    @GetMapping(produces = "application/json")
    private ResponseEntity<List<TaskResponseDTO>> get() {

        return ResponseEntity.ok(taskService.get());
    }

    @Operation(summary = "Create a task")
    @PostMapping(produces = "application/json")
    private ResponseEntity<String> store(@RequestBody TaskRequestDTO taskRequestDTO) {

        return ResponseEntity.ok(taskService.store(taskRequestDTO));
    }

    @Operation(summary = "Update a task")
    @PutMapping(produces = "application/json")
    private ResponseEntity<String> update(@RequestBody TaskResponseDTO taskResponseDTO) {

        return ResponseEntity.ok(taskService.update(taskResponseDTO));
    }

    @Operation(summary = "Delete a task by id")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    private ResponseEntity<String> delete(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.delete(id));
    }
}
