package org.example.springproject.exceptions;

public class CourseCapacityExceededException extends RuntimeException{
	public CourseCapacityExceededException(String s) {
		super(s);
	}
}
