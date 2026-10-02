package repository;

import model.Marks;
import java.util.List;

/**
 * MarksRepository interface - Data access layer for Marks objects.
 */
public interface MarksRepository {
    void save(Marks marks);
    Marks findById(String studentId, String courseId);
    List<Marks> findAll();
    List<Marks> findByStudentId(String studentId);
    List<Marks> findByCourseId(String courseId);
    void update(Marks marks);
    void delete(String studentId, String courseId);
    boolean exists(String studentId, String courseId);
}
