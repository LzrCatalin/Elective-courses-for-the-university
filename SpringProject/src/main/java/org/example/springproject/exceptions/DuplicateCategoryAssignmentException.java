package org.example.springproject.exceptions;

public class DuplicateCategoryAssignmentException extends RuntimeException{
	public DuplicateCategoryAssignmentException (String s) {
		super(s);
	}
}
