package com.lordeluan.helpdesk.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lordeluan.helpdesk.service.exceptions.ObjectnotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

//	Manipulador de exceção da classe abaixo
	@ExceptionHandler(ObjectnotFoundException.class)
	public ResponseEntity<StandardError> objectbotFoundException(ObjectnotFoundException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),
				"Object not found", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
