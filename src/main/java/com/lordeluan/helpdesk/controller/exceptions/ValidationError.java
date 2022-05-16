package com.lordeluan.helpdesk.controller.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String message) {
//		Instancia o objeto da nossa exceção recebendo as informação passada por paramentro e inserindo no arraylist de erro
		this.erros.add(new FieldMessage(fieldName, message));
	}
	
	
}

