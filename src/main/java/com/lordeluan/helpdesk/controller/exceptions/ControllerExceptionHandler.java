package com.lordeluan.helpdesk.controller.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lordeluan.helpdesk.service.exceptions.DataIntegrityViolationException;
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
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
				"Data violation", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
				"Value is required in field", "Falta informação em alguns campos", request.getRequestURI());
		
//		Percorre o corpo de retorno da exceção gerada, e para cada objeto/campo de exceção será inserido o nome do campo e a mensagem
//		no nosso arraylist de erro de validação
		for (FieldError x : ex.getBindingResult().getFieldErrors()) {
//			chama o metodo criado e passa o nome do campo e a mensagem padrão da exceção
			errors.addError(x.getField(), x.getDefaultMessage());
			
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
}
