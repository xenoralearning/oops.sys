package repository;

import model.Attendance;
import java.util.List;

/**
 * AttendanceRepository interface - Data access layer for Attendance objects.
 */
public interface AttendanceRepository {
    void save(Attendance attendance);
    Attendance findById(String studentId, String courseId);
    List<Attendance> findAll();
    List<Attendance> findByStudentId(String studentId);
    List<Attendance> findByCourseId(String courseId);
    void update(Attendance attendance);
    void delete(String studentId, String courseId);
    boolean exists(String studentId, String courseId);
}
