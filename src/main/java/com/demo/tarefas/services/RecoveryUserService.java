package com.demo.tarefas.services;

import com.demo.tarefas.domain.model.User;
import com.demo.tarefas.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryUserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Autowired
    public RecoveryUserService(TokenService tokenService, UserRepository userRepository, HttpServletRequest request) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.request = request;
    }

    public User getUser() {

        var token = tokenService.recoverToken(this.request);
        var login = tokenService.validateToken(token);

        return (User) userRepository.findByLogin(login);
    }

}
