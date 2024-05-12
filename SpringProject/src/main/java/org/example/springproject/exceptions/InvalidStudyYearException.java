package org.example.springproject.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidStudyYearException extends RuntimeException{
	private HttpStatus httpStatus;

	public InvalidStudyYearException() {}

	public InvalidStudyYearException(String message) {
		super(message);
	}

	public InvalidStudyYearException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
