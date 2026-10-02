package data;

import model.Student;
import model.Course;
import model.Marks;
import model.Attendance;
import service.StudentService;
import service.CourseService;
import service.MarksService;
import service.AttendanceService;

/**
 * DemoData class - Initializes demo/sample data for the Student Management System.
 * This ensures the application doesn't appear empty when first launched.
 * 
 * Demo data includes:
 * - 5 Sample Students
 * - 3 Sample Courses
 * - Sample Marks entries
 * - Sample Attendance records
 * 
 * Uses generic/fictional names - no real personal information.
 */
public class DemoData {
    private static boolean initialized = false;

    private StudentService studentService;
    private CourseService courseService;
    private MarksService marksService;
    private AttendanceService attendanceService;

    public DemoData(StudentService studentService, CourseService courseService,
                    MarksService marksService, AttendanceService attendanceService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.marksService = marksService;
        this.attendanceService = attendanceService;
    }

    // Initialize all demo data exactly once per application session.
    public synchronized void initializeDemoData() {
        if (initialized) {
            System.out.println("Demo data already initialized for this session.");
            return;
        }

        initializeDemoStudents();
        initializeDemoCourses();
        initializeDemoMarks();
        initializeDemoAttendance();
        initialized = true;
        System.out.println("✓ Demo data initialized successfully");
    }

    // Create sample students
    private void initializeDemoStudents() {
        try {
            // Student 1
            Student student1 = new Student(1, "Amit Kumar", 20, "9876543210", "amit@example.com",
                    "STU001", "Computer Science", 4, "A", 2022);
            studentService.addStudent(student1);

            // Student 2
            Student student2 = new Student(2, "Priya Singh", 21, "9876543211", "priya@example.com",
                    "STU002", "Information Technology", 4, "B", 2022);
            studentService.addStudent(student2);

            // Student 3
            Student student3 = new Student(3, "Rahul Patel", 19, "9876543212", "rahul@example.com",
                    "STU003", "Computer Science", 2, "A", 2023);
            studentService.addStudent(student3);

            // Student 4
            Student student4 = new Student(4, "Neha Gupta", 20, "9876543213", "neha@example.com",
                    "STU004", "Electronics", 3, "C", 2023);
            studentService.addStudent(student4);

            // Student 5
            Student student5 = new Student(5, "Vikram Rao", 21, "9876543214", "vikram@example.com",
                    "STU005", "Information Technology", 4, "A", 2022);
            studentService.addStudent(student5);

            System.out.println("  ✓ 5 demo students created");
        } catch (Exception e) {
            System.out.println("  ✗ Error creating demo students: " + e.getMessage());
        }
    }

    // Create sample courses
    private void initializeDemoCourses() {
        try {
            // Course 1
            Course course1 = new Course("CS101", "Object-Oriented Programming", 4, 2, "Computer Science");
            courseService.addCourse(course1);

            // Course 2
            Course course2 = new Course("CS201", "Database Management Systems", 3, 4, "Computer Science");
            courseService.addCourse(course2);

            // Course 3
            Course course3 = new Course("IT101", "Web Development", 4, 2, "Information Technology");
            courseService.addCourse(course3);

            System.out.println("  ✓ 3 demo courses created");
        } catch (Exception e) {
            System.out.println("  ✗ Error creating demo courses: " + e.getMessage());
        }
    }

    // Create sample marks
    private void initializeDemoMarks() {
        try {
            // Marks entries for students
            Marks marks1 = new Marks("STU001", "CS101", 35, 55); // 90%
            marksService.addMarks(marks1);

            Marks marks2 = new Marks("STU002", "CS101", 32, 48); // 80%
            marksService.addMarks(marks2);

            Marks marks3 = new Marks("STU003", "IT101", 38, 58); // 96%
            marksService.addMarks(marks3);

            Marks marks4 = new Marks("STU004", "CS201", 25, 40); // 65%
            marksService.addMarks(marks4);

            Marks marks5 = new Marks("STU005", "CS201", 38, 55); // 93%
            marksService.addMarks(marks5);

            Marks marks6 = new Marks("STU001", "IT101", 28, 45); // 73%
            marksService.addMarks(marks6);

            System.out.println("  ✓ 6 demo marks entries created");
        } catch (Exception e) {
            System.out.println("  ✗ Error creating demo marks: " + e.getMessage());
        }
    }

    // Create sample attendance records
    private void initializeDemoAttendance() {
        try {
            // Attendance records
            Attendance att1 = new Attendance("STU001", "CS101", 20, 18); // 90%
            attendanceService.addAttendance(att1);

            Attendance att2 = new Attendance("STU002", "CS101", 20, 16); // 80%
            attendanceService.addAttendance(att2);

            Attendance att3 = new Attendance("STU003", "IT101", 20, 19); // 95%
            attendanceService.addAttendance(att3);

            Attendance att4 = new Attendance("STU004", "CS201", 15, 10); // 66.67%
            attendanceService.addAttendance(att4);

            Attendance att5 = new Attendance("STU005", "CS201", 15, 15); // 100%
            attendanceService.addAttendance(att5);

            Attendance att6 = new Attendance("STU001", "IT101", 18, 16); // 88.89%
            attendanceService.addAttendance(att6);

            System.out.println("  ✓ 6 demo attendance records created");
        } catch (Exception e) {
            System.out.println("  ✗ Error creating demo attendance: " + e.getMessage());
        }
    }
}
