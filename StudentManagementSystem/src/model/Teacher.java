package model;

/**
 * Teacher class - Demonstrates Inheritance and Method Overriding.
 * Teacher extends Person (inheritance).
 * Teacher overrides displayDetails() differently than Student (method overriding with different behavior).
 * This allows runtime polymorphism - calling displayDetails() on a Person reference will
 * call the appropriate implementation based on actual object type (Student or Teacher).
 */
public class Teacher extends Person {
    private String employeeId;
    private String department;
    private String designation;

    // Constructor
    public Teacher(int id, String name, int age, String phone, String email,
                   String employeeId, String department, String designation) {
        super(id, name, age, phone, email);
        this.employeeId = employeeId;
        this.department = department;
        this.designation = designation;
    }

    // Encapsulation: Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Method Overriding: Different implementation than Student
    @Override
    public void displayDetails() {
        System.out.println("=== TEACHER DETAILS ===");
        System.out.println("Person ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Department: " + department);
        System.out.println("Designation: " + designation);
        System.out.println("========================");
    }

    @Override
    public String toString() {
        return employeeId + " - " + getName() + " (" + designation + ", " + department + ")";
    }
}
