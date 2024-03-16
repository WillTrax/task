package com.demo.tarefas.controllers;

import com.demo.tarefas.domain.dto.CreateUserDTO;
import com.demo.tarefas.domain.dto.LoginRequestDTO;
import com.demo.tarefas.domain.dto.LoginResponseDTO;
import com.demo.tarefas.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "User CRUD Routes", name = "User Controller")
@RequestMapping("auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a user")
    @PostMapping(produces = "application/json")
    private ResponseEntity<String> store(@RequestBody CreateUserDTO userDTO) {

        return ResponseEntity.ok(userService.store(userDTO));
    }

    @Operation(summary = "Login")
    @PostMapping(value = "/login", produces = "application/json")
    private ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping(produces = "application/json")
    private ResponseEntity<String> delete() {

        return ResponseEntity.ok(userService.delete());
    }

}
