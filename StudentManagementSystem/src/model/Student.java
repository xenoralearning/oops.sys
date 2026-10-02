package model;

/**
 * Student class - Demonstrates Inheritance and Method Overriding.
 * Student extends Person (inheritance).
 * Student overrides the abstract displayDetails() method from Person (method overriding).
 * This class demonstrates that a child class can reuse parent attributes and add its own.
 */
public class Student extends Person {
    private String studentId;
    private String department;
    private int semester;
    private String section;
    private int admissionYear;

    // Constructor
    public Student(int id, String name, int age, String phone, String email,
                   String studentId, String department, int semester, String section, int admissionYear) {
        super(id, name, age, phone, email);
        this.studentId = studentId;
        this.department = department;
        this.semester = semester;
        this.section = section;
        this.admissionYear = admissionYear;
    }

    // Encapsulation: Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    // Method Overriding: Child class provides its own implementation
    @Override
    public void displayDetails() {
        System.out.println("=== STUDENT DETAILS ===");
        System.out.println("Person ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Student ID: " + studentId);
        System.out.println("Department: " + department);
        System.out.println("Semester: " + semester);
        System.out.println("Section: " + section);
        System.out.println("Admission Year: " + admissionYear);
        System.out.println("=======================");
    }

    @Override
    public String toString() {
        return studentId + " - " + getName() + " (" + department + ", Sem " + semester + ")";
    }
}
