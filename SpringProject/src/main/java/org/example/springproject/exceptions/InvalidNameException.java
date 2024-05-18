package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class InvalidNameException extends RuntimeException{
	private HttpStatus httpStatus;

	public InvalidNameException() {}

	public InvalidNameException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public InvalidNameException(String s) {
		super(s);
	}

	public HttpStatusCode getHttpStatus() {
		return httpStatus;
	}
}
