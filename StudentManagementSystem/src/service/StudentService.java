package service;

import model.Student;
import repository.StudentRepository;
import repository.InMemoryStudentRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentService - Business logic layer for Student operations.
 * 
 * Demonstrates:
 * - Method Overloading: searchStudent() has multiple versions
 * - Service pattern: Separates business logic from GUI and data access
 * - Encapsulation of repository: Uses StudentRepository interface
 * 
 * Service is the middle layer between GUI and Repository:
 * GUI → StudentService → StudentRepository → Data Storage
 */
public class StudentService {
    private StudentRepository studentRepository;

    // Constructor with dependency injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Default constructor - uses InMemoryStudentRepository
    public StudentService() {
        this.studentRepository = new InMemoryStudentRepository();
    }

    // CRUD Operations
    public void addStudent(Student student) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (student.getAge() < 15 || student.getAge() > 60) {
            throw new IllegalArgumentException("Student age must be between 15 and 60");
        }
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) throws IllegalArgumentException {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found");
        }
        return student;
    }

    public void updateStudent(Student student) throws IllegalArgumentException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (!studentRepository.exists(student.getId())) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " not found");
        }
        studentRepository.update(student);
    }

    public void deleteStudent(int studentId) throws IllegalArgumentException {
        if (!studentRepository.exists(studentId)) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found");
        }
        studentRepository.delete(studentId);
    }

    // Method Overloading Example 1: Search by Student ID (int parameter)
    public List<Student> searchStudent(int studentId) {
        Student student = studentRepository.findById(studentId);
        List<Student> result = new ArrayList<>();
        if (student != null) {
            result.add(student);
        }
        return result;
    }

    // Method Overloading Example 2: Search by Student Name (String parameter)
    // This demonstrates compile-time polymorphism (method overloading)
    public List<Student> searchStudent(String name) {
        List<Student> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return result;
        }
        
        String searchName = name.trim().toLowerCase();
        for (Student student : studentRepository.findAll()) {
            if (student.getName().toLowerCase().contains(searchName)) {
                result.add(student);
            }
        }
        return result;
    }

    // Additional search by department
    public List<Student> searchByDepartment(String department) {
        List<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getDepartment().equalsIgnoreCase(department)) {
                result.add(student);
            }
        }
        return result;
    }

    // Get total number of students
    public int getTotalStudents() {
        return studentRepository.findAll().size();
    }

    // Check if student ID already exists
    public boolean studentIdExists(int studentId) {
        return studentRepository.exists(studentId);
    }
}
