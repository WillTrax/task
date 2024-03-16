package com.demo.tarefas.services;

import com.demo.tarefas.domain.dto.CreateUserDTO;
import com.demo.tarefas.domain.dto.LoginRequestDTO;
import com.demo.tarefas.domain.dto.LoginResponseDTO;
import com.demo.tarefas.domain.model.repository.UserRepository;
import com.demo.tarefas.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RecoveryUserService recoveryUserService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService, RecoveryUserService recoveryUserService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.recoveryUserService = recoveryUserService;
    }

    @Transactional
    public String store(CreateUserDTO userDTO) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
        User newUser = new User(userDTO.name(), userDTO.login(), encryptedPassword, LocalDateTime.now());

        userRepository.save(newUser);

        return "User create success";
    }

    @Transactional
    public String delete() {

        User user = recoveryUserService.getUser();
        userRepository.delete(user);

        return "User remove success";
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.password());
        var auth = this.authenticationManager
                .authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);

    }
}
