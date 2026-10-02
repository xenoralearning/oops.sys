package repository;

import model.Attendance;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryAttendanceRepository - Implementation of AttendanceRepository using ArrayList.
 */
public class InMemoryAttendanceRepository implements AttendanceRepository {
    private static ArrayList<Attendance> attendances = new ArrayList<>();

    @Override
    public void save(Attendance attendance) {
        // Check if attendance entry already exists
        for (Attendance a : attendances) {
            if (a.getStudentId().equals(attendance.getStudentId()) && 
                a.getCourseId().equals(attendance.getCourseId())) {
                return; // Already exists
            }
        }
        attendances.add(attendance);
    }

    @Override
    public Attendance findById(String studentId, String courseId) {
        for (Attendance attendance : attendances) {
            if (attendance.getStudentId().equals(studentId) && 
                attendance.getCourseId().equals(courseId)) {
                return attendance;
            }
        }
        return null;
    }

    @Override
    public List<Attendance> findAll() {
        return new ArrayList<>(attendances);
    }

    @Override
    public List<Attendance> findByStudentId(String studentId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getStudentId().equals(studentId)) {
                result.add(attendance);
            }
        }
        return result;
    }

    @Override
    public List<Attendance> findByCourseId(String courseId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getCourseId().equals(courseId)) {
                result.add(attendance);
            }
        }
        return result;
    }

    @Override
    public void update(Attendance attendance) {
        for (int i = 0; i < attendances.size(); i++) {
            Attendance a = attendances.get(i);
            if (a.getStudentId().equals(attendance.getStudentId()) && 
                a.getCourseId().equals(attendance.getCourseId())) {
                attendances.set(i, attendance);
                return;
            }
        }
    }

    @Override
    public void delete(String studentId, String courseId) {
        attendances.removeIf(a -> a.getStudentId().equals(studentId) && 
                                  a.getCourseId().equals(courseId));
    }

    @Override
    public boolean exists(String studentId, String courseId) {
        return findById(studentId, courseId) != null;
    }

    public void clear() {
        attendances.clear();
    }
}
