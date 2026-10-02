package gui;

import model.Marks;
import model.Attendance;
import service.StudentService;
import service.MarksService;
import service.AttendanceService;
import interface_.ReportGenerator;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ReportPanel - GUI for Report Generation
 * Demonstrates implementation of ReportGenerator interface
 */
public class ReportPanel extends JPanel {
    private StudentService studentService;
    private MarksService marksService;
    private AttendanceService attendanceService;
    private JTextArea reportTextArea;
    private JComboBox<String> reportTypeCombo;

    // Report generators implementing ReportGenerator interface
    private StudentReportGenerator studentReportGen;
    private MarksReportGenerator marksReportGen;
    private AttendanceReportGenerator attendanceReportGen;

    public ReportPanel(StudentService studentService, MarksService marksService, AttendanceService attendanceService) {
        this.studentService = studentService;
        this.marksService = marksService;
        this.attendanceService = attendanceService;

        // Initialize report generators
        this.studentReportGen = new StudentReportGenerator();
        this.marksReportGen = new MarksReportGenerator();
        this.attendanceReportGen = new AttendanceReportGenerator();

        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createControlPanel(), BorderLayout.WEST);
        add(createReportPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 126, 34));
        panel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel label = new JLabel("Reports");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.WEST);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 240, 241));
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel reportLabel = new JLabel("Select Report Type:");
        reportLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(reportLabel);

        reportTypeCombo = new JComboBox<>(new String[]{
                "Student Summary",
                "Marks Summary",
                "Attendance Summary",
                "Overall Performance"
        });
        reportTypeCombo.setMaximumSize(new Dimension(180, 30));
        panel.add(reportTypeCombo);

        panel.add(Box.createVerticalStrut(10));

        JButton generateButton = new JButton("Generate Report");
        generateButton.setFont(new Font("Arial", Font.BOLD, 12));
        generateButton.setBackground(new Color(230, 126, 34));
        generateButton.setForeground(Color.WHITE);
        generateButton.setMaximumSize(new Dimension(180, 35));
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(e -> generateReport());
        panel.add(generateButton);

        panel.add(Box.createVerticalStrut(10));

        JButton exportButton = new JButton("Export Report");
        exportButton.setFont(new Font("Arial", Font.BOLD, 12));
        exportButton.setBackground(new Color(149, 165, 166));
        exportButton.setForeground(Color.WHITE);
        exportButton.setMaximumSize(new Dimension(180, 35));
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Export feature coming soon!"));
        panel.add(exportButton);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        reportTextArea = new JTextArea();
        reportTextArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        reportTextArea.setEditable(false);
        reportTextArea.setText("Select a report type and click 'Generate Report' to view");

        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void generateReport() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        String report = "";

        switch (reportType) {
            case "Student Summary":
                report = studentReportGen.generateReport(studentService.getAllStudents());
                break;
            case "Marks Summary":
                report = marksReportGen.generateReport(marksService.getAllMarks());
                break;
            case "Attendance Summary":
                report = attendanceReportGen.generateReport(attendanceService.getAllAttendance());
                break;
            case "Overall Performance":
                report = generateOverallPerformanceReport();
                break;
        }

        reportTextArea.setText(report);
    }

    private String generateOverallPerformanceReport() {
        StringBuilder report = new StringBuilder();
        report.append("=".repeat(70)).append("\n");
        report.append("OVERALL STUDENT PERFORMANCE REPORT\n");
        report.append("=".repeat(70)).append("\n\n");

        try {
            var students = studentService.getAllStudents();
            report.append(String.format("Total Students: %d\n", students.size()));
            report.append(String.format("Total Courses: %d\n", 0)).append("\n");

            report.append("TOP PERFORMING STUDENTS:\n");
            report.append("-".repeat(70)).append("\n");

            for (var student : students) {
                try {
                    double avgMarks = marksService.calculateStudentAverageMarks(student.getStudentId());
                    double avgAttendance = attendanceService.getOverallAttendancePercentage(student.getStudentId());
                    report.append(String.format("%s: Avg Marks: %.2f%%, Attendance: %.2f%%\n",
                            student.getName(), avgMarks, avgAttendance));
                } catch (Exception e) {
                    report.append(String.format("%s: No data\n", student.getName()));
                }
            }

            report.append("\n").append("=".repeat(70)).append("\n");
        } catch (Exception e) {
            report.append("Error generating report: ").append(e.getMessage());
        }

        return report.toString();
    }

    // Report Generator Implementations (demonstrate interface implementation)

    private class StudentReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder();
            report.append("=".repeat(70)).append("\n");
            report.append("STUDENT SUMMARY REPORT\n");
            report.append("=".repeat(70)).append("\n\n");

            @SuppressWarnings("unchecked")
            List<model.Student> students = (List<model.Student>) data;

            if (students.isEmpty()) {
                report.append("No students found.\n");
            } else {
                report.append(String.format("%-10s %-20s %-15s %-20s\n", "ID", "Name", "Department", "Semester"));
                report.append("-".repeat(70)).append("\n");

                for (var student : students) {
                    report.append(String.format("%-10d %-20s %-15s %-20d\n",
                            student.getId(), student.getName(), student.getDepartment(), student.getSemester()));
                }
            }

            report.append("\n").append("=".repeat(70)).append("\n");
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Student Summary Report";
        }
    }

    private class MarksReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder();
            report.append("=".repeat(70)).append("\n");
            report.append("MARKS SUMMARY REPORT\n");
            report.append("=".repeat(70)).append("\n\n");

            @SuppressWarnings("unchecked")
            List<Marks> marks = (List<Marks>) data;

            if (marks.isEmpty()) {
                report.append("No marks records found.\n");
            } else {
                report.append(String.format("%-12s %-12s %-10s %-12s %-8s %-8s\n",
                        "Student", "Course", "Total", "Percentage", "Grade", "Status"));
                report.append("-".repeat(70)).append("\n");

                for (var mark : marks) {
                    report.append(String.format("%-12s %-12s %-10d %-12.2f %-8c %-8s\n",
                            mark.getStudentId(), mark.getCourseId(), mark.getTotalMarks(),
                            mark.getPercentage(), mark.getGrade(),
                            mark.isPassed() ? "PASS" : "FAIL"));
                }
            }

            report.append("\n").append("=".repeat(70)).append("\n");
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Marks Summary Report";
        }
    }

    private class AttendanceReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder();
            report.append("=".repeat(70)).append("\n");
            report.append("ATTENDANCE SUMMARY REPORT\n");
            report.append("=".repeat(70)).append("\n\n");

            @SuppressWarnings("unchecked")
            List<Attendance> attendances = (List<Attendance>) data;

            if (attendances.isEmpty()) {
                report.append("No attendance records found.\n");
            } else {
                report.append(String.format("%-12s %-12s %-12s %-15s %-12s\n",
                        "Student", "Course", "Attended", "Total", "Percentage"));
                report.append("-".repeat(70)).append("\n");

                for (var att : attendances) {
                    try {
                        report.append(String.format("%-12s %-12s %-12d %-15d %-12.2f\n",
                                att.getStudentId(), att.getCourseId(), att.getAttendedClasses(),
                                att.getTotalClasses(), att.calculatePercentage()));
                    } catch (Exception e) {
                        report.append(String.format("%-12s %-12s INVALID\n",
                                att.getStudentId(), att.getCourseId()));
                    }
                }
            }

            report.append("\n").append("=".repeat(70)).append("\n");
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Attendance Summary Report";
        }
    }
}
