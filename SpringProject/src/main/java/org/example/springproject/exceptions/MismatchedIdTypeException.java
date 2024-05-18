package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;

public class MismatchedIdTypeException extends RuntimeException{
	private HttpStatus httpStatus;

	public MismatchedIdTypeException() {}

	public MismatchedIdTypeException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public MismatchedIdTypeException(String s) {
		super(s);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
