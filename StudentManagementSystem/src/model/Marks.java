package model;

/**
 * Marks class - Demonstrates Method Overloading.
 * This class has multiple calculateGrade() methods with different parameters:
 * - calculateGrade(int marks) - takes marks as integer
 * - calculateGrade(double percentage) - takes percentage as double
 * Both methods perform different calculations but serve related purposes.
 * Method overloading is a compile-time (static) polymorphism feature.
 */
public class Marks {
    private String studentId;
    private String courseId;
    private int internalMarks;
    private int externalMarks;
    private int totalMarks;
    private double percentage;
    private char grade;

    public static final int MAX_INTERNAL = 40;
    public static final int MAX_EXTERNAL = 60;
    public static final int MAX_TOTAL = 100;
    public static final int PASS_MARKS = 40;

    // Constructor
    public Marks(String studentId, String courseId, int internalMarks, int externalMarks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.totalMarks = internalMarks + externalMarks;
        calculatePercentage();
        this.grade = calculateGrade(this.totalMarks);
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

    public int getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(int internalMarks) {
        this.internalMarks = internalMarks;
    }

    public int getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(int externalMarks) {
        this.externalMarks = externalMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public char getGrade() {
        return grade;
    }

    // Method 1: Calculate percentage
    public void calculatePercentage() {
        this.percentage = (double) totalMarks / MAX_TOTAL * 100;
    }

    // Update marks and recalculate
    public void updateMarks(int internalMarks, int externalMarks) {
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.totalMarks = internalMarks + externalMarks;
        calculatePercentage();
        this.grade = calculateGrade(this.totalMarks);
    }

    // Method Overloading Example 1: Grade by total marks (int parameter)
    public char calculateGrade(int marks) {
        if (marks >= 90) {
            return 'A';
        } else if (marks >= 80) {
            return 'B';
        } else if (marks >= 70) {
            return 'C';
        } else if (marks >= 60) {
            return 'D';
        } else if (marks >= 50) {
            return 'E';
        } else {
            return 'F';
        }
    }

    // Method Overloading Example 2: Grade by percentage (double parameter)
    // This is a different method with same name but different parameter type
    public char calculateGrade(double percentage) {
        if (percentage >= 90) {
            return 'A';
        } else if (percentage >= 80) {
            return 'B';
        } else if (percentage >= 70) {
            return 'C';
        } else if (percentage >= 60) {
            return 'D';
        } else if (percentage >= 50) {
            return 'E';
        } else {
            return 'F';
        }
    }

    // Check if student is pass or fail
    public boolean isPassed() {
        return totalMarks >= PASS_MARKS;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", total=" + totalMarks +
                ", percentage=" + String.format("%.2f", percentage) +
                ", grade=" + grade +
                '}';
    }
}
