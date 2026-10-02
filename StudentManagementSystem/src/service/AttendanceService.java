package service;

import model.Attendance;
import repository.AttendanceRepository;
import repository.InMemoryAttendanceRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceService - Business logic layer for Attendance operations.
 * Handles attendance tracking, percentage calculation, and eligibility checks.
 */
public class AttendanceService {
    private AttendanceRepository attendanceRepository;

    // Constructor with dependency injection
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // Default constructor
    public AttendanceService() {
        this.attendanceRepository = new InMemoryAttendanceRepository();
    }

    // Add attendance record
    public void addAttendance(Attendance attendance) throws IllegalArgumentException {
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance cannot be null");
        }
        validateAttendance(attendance);
        attendanceRepository.save(attendance);
    }

    // Validate attendance
    private void validateAttendance(Attendance attendance) throws IllegalArgumentException {
        if (attendance.getStudentId() == null || attendance.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        if (attendance.getCourseId() == null || attendance.getCourseId().trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be empty");
        }
        if (attendance.getTotalClasses() < 0) {
            throw new IllegalArgumentException("Total classes cannot be negative");
        }
        if (attendance.getAttendedClasses() < 0) {
            throw new IllegalArgumentException("Attended classes cannot be negative");
        }
        if (attendance.getAttendedClasses() > attendance.getTotalClasses()) {
            throw new IllegalArgumentException("Attended classes cannot be greater than total classes");
        }
    }

    // Get attendance for a student-course combination
    public Attendance getAttendance(String studentId, String courseId) throws IllegalArgumentException {
        Attendance attendance = attendanceRepository.findById(studentId, courseId);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance not found for Student: " + studentId + ", Course: " + courseId);
        }
        return attendance;
    }

    // Get all attendance records for a student
    public List<Attendance> getAttendanceForStudent(String studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    // Get all attendance records for a course
    public List<Attendance> getAttendanceForCourse(String courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }

    // Update attendance
    public void updateAttendance(String studentId, String courseId, int totalClasses, int attendedClasses) 
            throws IllegalArgumentException {
        Attendance attendance = attendanceRepository.findById(studentId, courseId);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance record not found");
        }
        
        // Validate new values
        if (attendedClasses > totalClasses || totalClasses < 0 || attendedClasses < 0) {
            throw new IllegalArgumentException("Invalid attendance values");
        }
        
        attendance.setTotalClasses(totalClasses);
        attendance.setAttendedClasses(attendedClasses);
        attendanceRepository.update(attendance);
    }

    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // Calculate attendance percentage for a student-course
    public double calculateAttendancePercentage(String studentId, String courseId) throws IllegalArgumentException {
        Attendance attendance = getAttendance(studentId, courseId);
        return attendance.calculatePercentage();
    }

    // Get attendance status
    public String getAttendanceStatus(String studentId, String courseId) throws IllegalArgumentException {
        Attendance attendance = getAttendance(studentId, courseId);
        return attendance.getAttendanceStatus();
    }

    // Check if student is eligible based on attendance
    public boolean isEligible(String studentId, String courseId) throws IllegalArgumentException {
        Attendance attendance = getAttendance(studentId, courseId);
        return attendance.isEligible();
    }

    // Get overall attendance percentage for a student
    public double getOverallAttendancePercentage(String studentId) throws IllegalArgumentException {
        List<Attendance> attendances = getAttendanceForStudent(studentId);
        if (attendances.isEmpty()) {
            throw new IllegalArgumentException("No attendance records found for student: " + studentId);
        }
        
        double totalPercentage = 0;
        for (Attendance attendance : attendances) {
            try {
                totalPercentage += attendance.calculatePercentage();
            } catch (IllegalArgumentException e) {
                // Skip invalid records
            }
        }
        return totalPercentage / attendances.size();
    }

    // Check if attendance record exists
    public boolean attendanceExists(String studentId, String courseId) {
        return attendanceRepository.exists(studentId, courseId);
    }
}
