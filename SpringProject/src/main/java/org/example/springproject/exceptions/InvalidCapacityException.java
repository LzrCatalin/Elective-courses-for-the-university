package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCapacityException extends RuntimeException{
	private HttpStatus httpStatus;

	public InvalidCapacityException() {}

	public InvalidCapacityException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public InvalidCapacityException(String s) {
		super(s);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
