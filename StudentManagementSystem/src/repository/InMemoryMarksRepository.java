package repository;

import model.Marks;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryMarksRepository - Implementation of MarksRepository using ArrayList.
 */
public class InMemoryMarksRepository implements MarksRepository {
    private static ArrayList<Marks> marks = new ArrayList<>();

    @Override
    public void save(Marks mark) {
        // Check if marks entry already exists
        for (Marks m : marks) {
            if (m.getStudentId().equals(mark.getStudentId()) && 
                m.getCourseId().equals(mark.getCourseId())) {
                return; // Already exists
            }
        }
        marks.add(mark);
    }

    @Override
    public Marks findById(String studentId, String courseId) {
        for (Marks mark : marks) {
            if (mark.getStudentId().equals(studentId) && 
                mark.getCourseId().equals(courseId)) {
                return mark;
            }
        }
        return null;
    }

    @Override
    public List<Marks> findAll() {
        return new ArrayList<>(marks);
    }

    @Override
    public List<Marks> findByStudentId(String studentId) {
        List<Marks> result = new ArrayList<>();
        for (Marks mark : marks) {
            if (mark.getStudentId().equals(studentId)) {
                result.add(mark);
            }
        }
        return result;
    }

    @Override
    public List<Marks> findByCourseId(String courseId) {
        List<Marks> result = new ArrayList<>();
        for (Marks mark : marks) {
            if (mark.getCourseId().equals(courseId)) {
                result.add(mark);
            }
        }
        return result;
    }

    @Override
    public void update(Marks mark) {
        for (int i = 0; i < marks.size(); i++) {
            Marks m = marks.get(i);
            if (m.getStudentId().equals(mark.getStudentId()) && 
                m.getCourseId().equals(mark.getCourseId())) {
                marks.set(i, mark);
                return;
            }
        }
    }

    @Override
    public void delete(String studentId, String courseId) {
        marks.removeIf(m -> m.getStudentId().equals(studentId) && 
                           m.getCourseId().equals(courseId));
    }

    @Override
    public boolean exists(String studentId, String courseId) {
        return findById(studentId, courseId) != null;
    }

    public void clear() {
        marks.clear();
    }
}
