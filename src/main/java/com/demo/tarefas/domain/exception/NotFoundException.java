package com.demo.tarefas.domain.exception;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}

}
