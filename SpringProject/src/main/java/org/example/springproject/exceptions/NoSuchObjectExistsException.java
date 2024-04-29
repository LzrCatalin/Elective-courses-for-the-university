package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class NoSuchObjectExistsException extends RuntimeException {
	private HttpStatus httpStatus;

	public NoSuchObjectExistsException() {}

	public NoSuchObjectExistsException(String msg) {
		super(msg);
	}

	public NoSuchObjectExistsException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;

	}

	public HttpStatusCode getHttpStatus() {
		return httpStatus;
	}
}
