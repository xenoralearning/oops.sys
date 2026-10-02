package repository;

import model.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryStudentRepository - Implementation of StudentRepository using ArrayList.
 * This is the current data storage solution for the application.
 * 
 * Future MongoDB Integration:
 * When MongoDB is integrated, we can create MongoStudentRepository implementing
 * the same StudentRepository interface without changing the service layer.
 * 
 * This demonstrates:
 * - Collections (ArrayList)
 * - Implementation of an interface
 * - Separation of concerns (data access layer)
 */
public class InMemoryStudentRepository implements StudentRepository {
    private static ArrayList<Student> students = new ArrayList<>();
    private static int nextId = 1;

    @Override
    public void save(Student student) {
        if (student.getId() == 0) {
            student.setId(nextId++);
        }
        students.add(student);
    }

    @Override
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    @Override
    public boolean exists(int id) {
        return findById(id) != null;
    }

    // Helper method to get total students
    public int getTotalStudents() {
        return students.size();
    }

    // Clear all students (useful for testing)
    public void clear() {
        students.clear();
        nextId = 1;
    }
}
