import data.AppSession;
import gui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Main.java - Entry point for Student Management System
 * 
 * This application demonstrates Object-Oriented Programming concepts in Java:
 * - Classes and Objects
 * - Encapsulation (private fields + getters/setters)
 * - Inheritance (Student/Teacher extend Person)
 * - Polymorphism (Parent reference to child objects)
 * - Method Overloading (multiple searchStudent methods)
 * - Method Overriding (displayDetails() in child classes)
 * - Abstraction (abstract Person class)
 * - Interfaces (ReportGenerator interface)
 * - Collections (ArrayList for storage)
 * - Exception Handling (proper error management)
 */
public class Main {
    public static void main(String[] args) {
        AppSession.initialize();

        SwingUtilities.invokeLater(() -> {
            System.out.println("Launching Student Management System...");
            new LoginFrame();
        });
    }
}
