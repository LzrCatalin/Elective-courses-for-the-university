package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;

public class MismatchedFacultySectionException extends RuntimeException{
	private HttpStatus httpStatus;

	public MismatchedFacultySectionException() {}

	public MismatchedFacultySectionException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
