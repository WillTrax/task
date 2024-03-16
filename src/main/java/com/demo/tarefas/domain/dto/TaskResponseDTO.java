package com.demo.tarefas.domain.dto;

import com.demo.tarefas.domain.enumerated.Status;

import java.time.LocalDateTime;

public record TaskResponseDTO(Long id, String title, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
