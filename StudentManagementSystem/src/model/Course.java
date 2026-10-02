package model;

/**
 * Course class - Demonstrates proper use of classes with encapsulation.
 * All fields are private with appropriate getters and setters.
 * This class represents a course offered by the institution.
 */
public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private int semester;
    private String department;

    // Constructor
    public Course(String courseId, String courseName, int credits, int semester, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.department = department;
    }

    // Encapsulation: Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName + " (" + credits + " credits, Sem " + semester + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() {
        return courseId.hashCode();
    }
}
