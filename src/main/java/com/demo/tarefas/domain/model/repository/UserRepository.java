package com.demo.tarefas.domain.model.repository;

import com.demo.tarefas.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
        UserDetails findByLogin(String login);

    }
