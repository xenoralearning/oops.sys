package repository;

import model.Course;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryCourseRepository - Implementation of CourseRepository using ArrayList.
 * Provides in-memory storage for Course objects.
 * Like StudentRepository, this can be replaced with MongodbCourseRepository later.
 */
public class InMemoryCourseRepository implements CourseRepository {
    private static ArrayList<Course> courses = new ArrayList<>();

    @Override
    public void save(Course course) {
        // Check if course already exists
        for (Course c : courses) {
            if (c.getCourseId().equals(course.getCourseId())) {
                return; // Course already exists
            }
        }
        courses.add(course);
    }

    @Override
    public Course findById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    @Override
    public void update(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(course.getCourseId())) {
                courses.set(i, course);
                return;
            }
        }
    }

    @Override
    public void delete(String courseId) {
        courses.removeIf(course -> course.getCourseId().equals(courseId));
    }

    @Override
    public boolean exists(String courseId) {
        return findById(courseId) != null;
    }

    // Helper method to get total courses
    public int getTotalCourses() {
        return courses.size();
    }

    // Clear all courses
    public void clear() {
        courses.clear();
    }
}
