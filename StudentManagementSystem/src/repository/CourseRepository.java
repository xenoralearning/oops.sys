package repository;

import model.Course;
import java.util.List;

/**
 * CourseRepository interface - Data access layer for Course objects.
 * Similar to StudentRepository, this allows easy switching between different
 * persistence implementations (in-memory vs MongoDB, etc.)
 */
public interface CourseRepository {
    /**
     * Save a new course or update existing one
     */
    void save(Course course);

    /**
     * Find a course by ID
     */
    Course findById(String courseId);

    /**
     * Find all courses
     */
    List<Course> findAll();

    /**
     * Update an existing course
     */
    void update(Course course);

    /**
     * Delete a course by ID
     */
    void delete(String courseId);

    /**
     * Check if course exists
     */
    boolean exists(String courseId);
}
