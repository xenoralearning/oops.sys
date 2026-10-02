package service;

import model.Marks;
import repository.MarksRepository;
import repository.InMemoryMarksRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * MarksService - Business logic layer for Marks operations.
 * Handles marks calculation, grading, and retrieval.
 */
public class MarksService {
    private MarksRepository marksRepository;

    // Constructor with dependency injection
    public MarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    // Default constructor
    public MarksService() {
        this.marksRepository = new InMemoryMarksRepository();
    }

    // Add marks entry
    public void addMarks(Marks marks) throws IllegalArgumentException {
        if (marks == null) {
            throw new IllegalArgumentException("Marks cannot be null");
        }
        validateMarks(marks);
        marksRepository.save(marks);
    }

    // Validate marks
    private void validateMarks(Marks marks) throws IllegalArgumentException {
        if (marks.getStudentId() == null || marks.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        if (marks.getCourseId() == null || marks.getCourseId().trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be empty");
        }
        if (marks.getInternalMarks() < 0 || marks.getInternalMarks() > Marks.MAX_INTERNAL) {
            throw new IllegalArgumentException("Internal marks must be between 0 and " + Marks.MAX_INTERNAL);
        }
        if (marks.getExternalMarks() < 0 || marks.getExternalMarks() > Marks.MAX_EXTERNAL) {
            throw new IllegalArgumentException("External marks must be between 0 and " + Marks.MAX_EXTERNAL);
        }
    }

    // Get marks for a student-course combination
    public Marks getMarks(String studentId, String courseId) throws IllegalArgumentException {
        Marks marks = marksRepository.findById(studentId, courseId);
        if (marks == null) {
            throw new IllegalArgumentException("Marks not found for Student: " + studentId + ", Course: " + courseId);
        }
        return marks;
    }

    // Get all marks for a student
    public List<Marks> getMarksForStudent(String studentId) {
        return marksRepository.findByStudentId(studentId);
    }

    // Get all marks for a course
    public List<Marks> getMarksForCourse(String courseId) {
        return marksRepository.findByCourseId(courseId);
    }

    // Update marks
    public void updateMarks(String studentId, String courseId, int internalMarks, int externalMarks) 
            throws IllegalArgumentException {
        Marks marks = marksRepository.findById(studentId, courseId);
        if (marks == null) {
            throw new IllegalArgumentException("Marks entry not found");
        }
        marks.updateMarks(internalMarks, externalMarks);
        marksRepository.update(marks);
    }

    // Get all marks
    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }

    // Calculate results for a student
    public List<Marks> calculateStudentResults(String studentId) {
        List<Marks> studentMarks = marksRepository.findByStudentId(studentId);
        List<Marks> results = new ArrayList<>();
        
        for (Marks mark : studentMarks) {
            if (mark.isPassed()) {
                results.add(mark);
            }
        }
        return results;
    }

    // Calculate average marks for a student
    public double calculateStudentAverageMarks(String studentId) throws IllegalArgumentException {
        List<Marks> studentMarks = marksRepository.findByStudentId(studentId);
        if (studentMarks.isEmpty()) {
            throw new IllegalArgumentException("No marks found for student: " + studentId);
        }
        
        double sum = 0;
        for (Marks mark : studentMarks) {
            sum += mark.getTotalMarks();
        }
        return sum / studentMarks.size();
    }

    // Check if marks entry exists
    public boolean marksExists(String studentId, String courseId) {
        return marksRepository.exists(studentId, courseId);
    }
}
