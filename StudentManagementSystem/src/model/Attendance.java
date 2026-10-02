package model;

/**
 * Attendance class - Demonstrates proper validation and exception handling.
 * This class tracks attendance for a student in a specific course.
 * It includes methods to calculate attendance percentage and status.
 */
public class Attendance {
    private String studentId;
    private String courseId;
    private int totalClasses;
    private int attendedClasses;

    public static final double ELIGIBLE_PERCENTAGE = 75.0;

    // Constructor
    public Attendance(String studentId, String courseId, int totalClasses, int attendedClasses) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
    }

    // Encapsulation: Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    // Calculate attendance percentage
    // Exception handling: Check for division by zero
    public double calculatePercentage() throws IllegalArgumentException {
        if (totalClasses <= 0) {
            throw new IllegalArgumentException("Total classes must be greater than 0");
        }
        if (attendedClasses > totalClasses) {
            throw new IllegalArgumentException("Attended classes cannot be greater than total classes");
        }
        if (attendedClasses < 0 || totalClasses < 0) {
            throw new IllegalArgumentException("Classes cannot be negative");
        }
        return (double) attendedClasses / totalClasses * 100;
    }

    // Get attendance status based on percentage
    public String getAttendanceStatus() {
        try {
            double percentage = calculatePercentage();
            if (percentage >= ELIGIBLE_PERCENTAGE) {
                return "ELIGIBLE";
            } else {
                return "NOT ELIGIBLE";
            }
        } catch (IllegalArgumentException e) {
            return "INVALID";
        }
    }

    // Check if attendance is eligible
    public boolean isEligible() {
        try {
            return calculatePercentage() >= ELIGIBLE_PERCENTAGE;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        try {
            return "Attendance{" +
                    "studentId='" + studentId + '\'' +
                    ", courseId='" + courseId + '\'' +
                    ", attended=" + attendedClasses + "/" + totalClasses +
                    ", percentage=" + String.format("%.2f", calculatePercentage()) + "%" +
                    ", status=" + getAttendanceStatus() +
                    '}';
        } catch (IllegalArgumentException e) {
            return "Attendance{studentId='" + studentId + "', courseId='" + courseId + "', status=INVALID}";
        }
    }
}
