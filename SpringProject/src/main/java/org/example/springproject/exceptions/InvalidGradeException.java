package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidGradeException extends RuntimeException{
	private HttpStatus httpStatus;

	public InvalidGradeException() {}

	public InvalidGradeException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatusCode getHttpStatus() {
		return httpStatus;
	}

}
