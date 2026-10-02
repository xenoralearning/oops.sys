package data;

import service.AttendanceService;
import service.CourseService;
import service.MarksService;
import service.StudentService;

public final class AppSession {
    private static final Object LOCK = new Object();

    private static StudentService studentService;
    private static CourseService courseService;
    private static MarksService marksService;
    private static AttendanceService attendanceService;
    private static boolean demoDataInitialized = false;

    private AppSession() {
    }

    public static void initialize() {
        synchronized (LOCK) {
            if (studentService == null) {
                studentService = new StudentService();
            }
            if (courseService == null) {
                courseService = new CourseService();
            }
            if (marksService == null) {
                marksService = new MarksService();
            }
            if (attendanceService == null) {
                attendanceService = new AttendanceService();
            }
            if (!demoDataInitialized) {
                DemoData demoData = new DemoData(studentService, courseService, marksService, attendanceService);
                demoData.initializeDemoData();
                demoDataInitialized = true;
            }
        }
    }

    public static StudentService getStudentService() {
        initialize();
        return studentService;
    }

    public static CourseService getCourseService() {
        initialize();
        return courseService;
    }

    public static MarksService getMarksService() {
        initialize();
        return marksService;
    }

    public static AttendanceService getAttendanceService() {
        initialize();
        return attendanceService;
    }

    public static boolean isDemoDataInitialized() {
        return demoDataInitialized;
    }
}
