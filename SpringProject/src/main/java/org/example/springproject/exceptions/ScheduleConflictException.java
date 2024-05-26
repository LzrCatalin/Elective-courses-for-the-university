package org.example.springproject.exceptions;

public class ScheduleConflictException extends RuntimeException{
	public ScheduleConflictException() {}
	public ScheduleConflictException(String s) {
		super(s);
	}
}
