package org.example.springproject.exceptions;

public class DuplicateCourseAssignmentException extends RuntimeException{
	public DuplicateCourseAssignmentException(String s) {
		super(s);
	}
}
