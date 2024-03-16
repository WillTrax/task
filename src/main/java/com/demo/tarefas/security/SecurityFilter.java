package com.demo.tarefas.security;

import com.demo.tarefas.domain.model.repository.UserRepository;
import com.demo.tarefas.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Autowired
    public SecurityFilter(TokenService tokenService, UserRepository userRepository, HttpServletRequest request) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = tokenService.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);

            UserDetails user = userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public User getUser() {

        var token = tokenService.recoverToken(this.request);
        var login = tokenService.validateToken(token);
        User user = (User) userRepository.findByLogin(login);


        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return user;
    }
}