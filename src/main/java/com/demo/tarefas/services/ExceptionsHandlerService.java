package com.demo.tarefas.services;

import com.demo.tarefas.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ExceptionsHandlerService {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> ValidException(SQLIntegrityConstraintViolationException ex) {

        return new ResponseEntity<>(ex.getLocalizedMessage() ,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> ValidException(NotFoundException ex) {

        return new ResponseEntity<>(ex.getMessage() ,HttpStatus.OK);
    }
}
