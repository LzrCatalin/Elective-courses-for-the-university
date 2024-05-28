package org.example.springproject.utilities;

import org.example.springproject.exceptions.InvalidTimeException;

public class TimeValidator {
	public static void validateTimeFormat(String time) {
		if (!time.matches("\\d{2}:\\d{2}")) {
			throw new InvalidTimeException("Time is not in the correct format. (HH:mm)");
		}

		// Split and get hour and minutes from a string
		String[] parts = time.split(":");
		int hour = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);

		// Validate hour and minuts
		if (hour < 0 || hour > 24 || minutes < 0 || minutes > 59) {
			throw new InvalidTimeException("Hour must be between 0 and 23 and minute must be between 0 and 59.");
		}
	}
}
