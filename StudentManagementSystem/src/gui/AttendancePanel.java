package gui;

import model.Attendance;
import service.StudentService;
import service.CourseService;
import service.AttendanceService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * AttendancePanel - GUI for Attendance Management
 */
public class AttendancePanel extends JPanel {
    private StudentService studentService;
    private CourseService courseService;
    private AttendanceService attendanceService;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> studentCombo, courseCombo;
    private JTextField totalClassesField, attendedClassesField;
    private JLabel percentageLabel, statusLabel;

    public AttendancePanel(StudentService studentService, CourseService courseService, AttendanceService attendanceService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.attendanceService = attendanceService;
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(241, 196, 15));
        panel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel label = new JLabel("Attendance Management");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.WEST);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 240, 241));
        panel.setPreferredSize(new Dimension(280, getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel formLabel = new JLabel("RECORD ATTENDANCE");
        formLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(formLabel);

        // Student selection
        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(studentLabel);
        studentCombo = new JComboBox<>();
        studentCombo.setMaximumSize(new Dimension(250, 25));
        refreshStudentCombo();
        panel.add(studentCombo);

        // Course selection
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(courseLabel);
        courseCombo = new JComboBox<>();
        courseCombo.setMaximumSize(new Dimension(250, 25));
        refreshCourseCombo();
        panel.add(courseCombo);

        panel.add(Box.createVerticalStrut(10));

        // Attendance input
        JLabel totalLabel = new JLabel("Total Classes:");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(totalLabel);
        totalClassesField = new JTextField(15);
        totalClassesField.setMaximumSize(new Dimension(250, 22));
        panel.add(totalClassesField);

        JLabel attendedLabel = new JLabel("Attended Classes:");
        attendedLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(attendedLabel);
        attendedClassesField = new JTextField(15);
        attendedClassesField.setMaximumSize(new Dimension(250, 22));
        panel.add(attendedClassesField);

        panel.add(Box.createVerticalStrut(15));

        // Results display
        JLabel resultsLabel = new JLabel("ATTENDANCE INFO");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(resultsLabel);

        percentageLabel = new JLabel("Percentage: -");
        percentageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(percentageLabel);

        statusLabel = new JLabel("Status: -");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(new Color(241, 196, 15));
        panel.add(statusLabel);

        panel.add(Box.createVerticalStrut(15));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setMaximumSize(new Dimension(250, 80));
        buttonPanel.add(createButton("Calculate", e -> calculateAttendance()));
        buttonPanel.add(createButton("Save", e -> saveAttendance()));
        buttonPanel.add(createButton("Update", e -> updateAttendance()));
        buttonPanel.add(createButton("Clear", e -> clearForm()));
        panel.add(buttonPanel);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(new Color(241, 196, 15));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Student", "Course", "Total", "Attended", "Percentage", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);
        attendanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attendanceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = attendanceTable.getSelectedRow();
                if (row >= 0) {
                    loadAttendanceToForm(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void calculateAttendance() {
        try {
            if (totalClassesField.getText().isEmpty() || attendedClassesField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both total and attended classes");
                return;
            }

            int total = Integer.parseInt(totalClassesField.getText());
            int attended = Integer.parseInt(attendedClassesField.getText());

            Attendance attendance = new Attendance("temp", "temp", total, attended);
            double percentage = attendance.calculatePercentage();
            percentageLabel.setText("Percentage: " + String.format("%.2f", percentage) + "%");
            statusLabel.setText("Status: " + attendance.getAttendanceStatus());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAttendance() {
        try {
            if (studentCombo.getSelectedItem() == null || courseCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select student and course");
                return;
            }

            int total = Integer.parseInt(totalClassesField.getText());
            int attended = Integer.parseInt(attendedClassesField.getText());

            String[] student = studentCombo.getSelectedItem().toString().split(" - ");
            String[] course = courseCombo.getSelectedItem().toString().split(" - ");

            Attendance attendance = new Attendance(student[0], course[0], total, attended);
            attendanceService.addAttendance(attendance);
            JOptionPane.showMessageDialog(this, "Attendance recorded successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAttendance() {
        try {
            if (studentCombo.getSelectedItem() == null || courseCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select student and course");
                return;
            }

            int total = Integer.parseInt(totalClassesField.getText());
            int attended = Integer.parseInt(attendedClassesField.getText());

            String[] student = studentCombo.getSelectedItem().toString().split(" - ");
            String[] course = courseCombo.getSelectedItem().toString().split(" - ");

            attendanceService.updateAttendance(student[0], course[0], total, attended);
            JOptionPane.showMessageDialog(this, "Attendance updated successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        totalClassesField.setText("");
        attendedClassesField.setText("");
        percentageLabel.setText("Percentage: -");
        statusLabel.setText("Status: -");
    }

    private void loadAttendanceToForm(int row) {
        totalClassesField.setText(tableModel.getValueAt(row, 2).toString());
        attendedClassesField.setText(tableModel.getValueAt(row, 3).toString());
    }

    private void refreshStudentCombo() {
        studentCombo.removeAllItems();
        for (var student : studentService.getAllStudents()) {
            studentCombo.addItem(student.getStudentId() + " - " + student.getName());
        }
    }

    private void refreshCourseCombo() {
        courseCombo.removeAllItems();
        for (var course : courseService.getAllCourses()) {
            courseCombo.addItem(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Attendance att : attendanceService.getAllAttendance()) {
            try {
                tableModel.addRow(new Object[]{
                        att.getStudentId(),
                        att.getCourseId(),
                        att.getTotalClasses(),
                        att.getAttendedClasses(),
                        String.format("%.2f", att.calculatePercentage()),
                        att.getAttendanceStatus()
                });
            } catch (Exception e) {
                // Skip invalid records
            }
        }
    }
}
