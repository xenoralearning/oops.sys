package repository;

import model.Student;
import java.util.List;

/**
 * StudentRepository interface - Demonstrates Repository Pattern.
 * This interface defines the contract for any Student data storage implementation.
 * 
 * Benefits:
 * - Allows switching between InMemoryStudentRepository and MongoStudentRepository
 *   without changing the service layer or GUI
 * - Clear separation between business logic and data access logic
 * - Future MongoDB integration becomes straightforward
 */
public interface StudentRepository {
    /**
     * Save a new student or update existing one
     */
    void save(Student student);

    /**
     * Find a student by ID
     */
    Student findById(int id);

    /**
     * Find all students
     */
    List<Student> findAll();

    /**
     * Update an existing student
     */
    void update(Student student);

    /**
     * Delete a student by ID
     */
    void delete(int id);

    /**
     * Check if student exists
     */
    boolean exists(int id);
}
