package org.example.springproject.utilities;

public class NameValidator {
	public static boolean validateString(String s) {
		// Regular expressions to check for letters and digits
		boolean hasLetter = s.matches(".*[a-zA-Z].*");
		boolean hasDigit = s.matches(".*\\d.*");

		// Check if string is valid (letters only or letters and digits)
		if (hasDigit && !hasLetter) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}
}
