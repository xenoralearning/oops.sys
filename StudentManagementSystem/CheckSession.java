import data.AppSession;
import model.Student;

public class CheckSession {
    public static void main(String[] args) {
        AppSession.initialize();
        System.out.println("initial=" + AppSession.getStudentService().getTotalStudents());
        AppSession.initialize();
        System.out.println("second_init=" + AppSession.getStudentService().getTotalStudents());

        AppSession.getStudentService().addStudent(new Student(999, "TEST001", 20, "9999999999", "test@example.com", "TEST001", "Computer Science", 4, "A", 2024));
        System.out.println("after_add=" + AppSession.getStudentService().getTotalStudents());

        AppSession.initialize();
        System.out.println("after_reinit=" + AppSession.getStudentService().getTotalStudents());
    }
}
