package util;

import java.util.regex.Pattern;

/**
 * ValidationUtil - Reusable validation methods for the application.
 * 
 * Centralizes validation logic so it's not duplicated in multiple GUI classes.
 * This demonstrates the importance of code reusability and maintaining DRY principle.
 */
public class ValidationUtil {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^[0-9]{10}$");
    private static final Pattern STUDENT_ID_PATTERN = 
            Pattern.compile("^STU[0-9]{3,}$");
    private static final Pattern COURSE_ID_PATTERN = 
            Pattern.compile("^[A-Z]{2}[0-9]{3}$");

    /**
     * Validate if a string is not empty
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validate age
     */
    public static boolean isValidAge(int age) {
        return age >= 15 && age <= 60;
    }

    /**
     * Validate student ID format
     */
    public static boolean isValidStudentId(String studentId) {
        if (!isNotEmpty(studentId)) {
            return false;
        }
        return STUDENT_ID_PATTERN.matcher(studentId).matches();
    }

    /**
     * Validate course ID format
     */
    public static boolean isValidCourseId(String courseId) {
        if (!isNotEmpty(courseId)) {
            return false;
        }
        return COURSE_ID_PATTERN.matcher(courseId).matches();
    }

    /**
     * Validate credits (1-10)
     */
    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 10;
    }

    /**
     * Validate semester (1-8)
     */
    public static boolean isValidSemester(int semester) {
        return semester > 0 && semester <= 8;
    }

    /**
     * Validate marks (0-100)
     */
    public static boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;
    }

    /**
     * Validate internal marks (0-40)
     */
    public static boolean isValidInternalMarks(int marks) {
        return marks >= 0 && marks <= 40;
    }

    /**
     * Validate external marks (0-60)
     */
    public static boolean isValidExternalMarks(int marks) {
        return marks >= 0 && marks <= 60;
    }

    /**
     * Validate attendance values
     */
    public static boolean isValidAttendance(int totalClasses, int attendedClasses) {
        return totalClasses > 0 && attendedClasses >= 0 && attendedClasses <= totalClasses;
    }

    /**
     * Validate integer input from string
     */
    public static boolean isValidInteger(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parse integer safely
     */
    public static int parseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get validation error message
     */
    public static String getValidationError(String fieldName, String reason) {
        return fieldName + ": " + reason;
    }
}
