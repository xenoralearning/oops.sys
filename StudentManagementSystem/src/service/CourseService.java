package service;

import model.Course;
import repository.CourseRepository;
import repository.InMemoryCourseRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseService - Business logic layer for Course operations.
 * Follows the same architecture as StudentService.
 */
public class CourseService {
    private CourseRepository courseRepository;

    // Constructor with dependency injection
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Default constructor - uses InMemoryCourseRepository
    public CourseService() {
        this.courseRepository = new InMemoryCourseRepository();
    }

    // CRUD Operations
    public void addCourse(Course course) throws IllegalArgumentException {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (course.getCredits() <= 0 || course.getCredits() > 10) {
            throw new IllegalArgumentException("Credits must be between 1 and 10");
        }
        if (courseRepository.exists(course.getCourseId())) {
            throw new IllegalArgumentException("Course with ID " + course.getCourseId() + " already exists");
        }
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String courseId) throws IllegalArgumentException {
        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }
        return course;
    }

    public void updateCourse(Course course) throws IllegalArgumentException {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (!courseRepository.exists(course.getCourseId())) {
            throw new IllegalArgumentException("Course with ID " + course.getCourseId() + " not found");
        }
        courseRepository.update(course);
    }

    public void deleteCourse(String courseId) throws IllegalArgumentException {
        if (!courseRepository.exists(courseId)) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }
        courseRepository.delete(courseId);
    }

    // Search methods
    public List<Course> searchCourse(String courseId) {
        List<Course> result = new ArrayList<>();
        Course course = courseRepository.findById(courseId);
        if (course != null) {
            result.add(course);
        }
        return result;
    }

    public List<Course> searchByCourseName(String name) {
        List<Course> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return result;
        }
        
        String searchName = name.trim().toLowerCase();
        for (Course course : courseRepository.findAll()) {
            if (course.getCourseName().toLowerCase().contains(searchName)) {
                result.add(course);
            }
        }
        return result;
    }

    public List<Course> searchBySemester(int semester) {
        List<Course> result = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getSemester() == semester) {
                result.add(course);
            }
        }
        return result;
    }

    public List<Course> searchByDepartment(String department) {
        List<Course> result = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getDepartment().equalsIgnoreCase(department)) {
                result.add(course);
            }
        }
        return result;
    }

    // Get total number of courses
    public int getTotalCourses() {
        return courseRepository.findAll().size();
    }

    // Check if course ID already exists
    public boolean courseIdExists(String courseId) {
        return courseRepository.exists(courseId);
    }
}
