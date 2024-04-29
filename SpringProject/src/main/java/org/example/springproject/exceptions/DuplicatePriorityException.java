package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatePriorityException extends RuntimeException{
	private HttpStatus httpStatus;

	public DuplicatePriorityException() {}

	public DuplicatePriorityException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
