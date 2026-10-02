package gui;

import model.Marks;
import service.StudentService;
import service.CourseService;
import service.MarksService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * MarksPanel - GUI for Marks Management and Grade Calculation
 * Demonstrates method overloading and grade calculation
 */
public class MarksPanel extends JPanel {
    private StudentService studentService;
    private CourseService courseService;
    private MarksService marksService;
    private JTable marksTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> studentCombo, courseCombo;
    private JTextField internalField, externalField;
    private JLabel totalLabel, percentageLabel, gradeLabel, statusLabel;

    public MarksPanel(StudentService studentService, CourseService courseService, MarksService marksService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.marksService = marksService;
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(155, 89, 182));
        panel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel label = new JLabel("Marks Management");
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

        JLabel formLabel = new JLabel("ENTER MARKS");
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

        // Marks input
        JLabel internalLabel = new JLabel("Internal Marks (0-40):");
        internalLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(internalLabel);
        internalField = new JTextField(15);
        internalField.setMaximumSize(new Dimension(250, 22));
        panel.add(internalField);

        JLabel externalLabel = new JLabel("External Marks (0-60):");
        externalLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(externalLabel);
        externalField = new JTextField(15);
        externalField.setMaximumSize(new Dimension(250, 22));
        panel.add(externalField);

        panel.add(Box.createVerticalStrut(15));

        // Results display
        JLabel resultsLabel = new JLabel("RESULTS");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(resultsLabel);

        totalLabel = new JLabel("Total: -");
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(totalLabel);

        percentageLabel = new JLabel("Percentage: -");
        percentageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(percentageLabel);

        gradeLabel = new JLabel("Grade: -");
        gradeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gradeLabel.setForeground(new Color(155, 89, 182));
        panel.add(gradeLabel);

        statusLabel = new JLabel("Status: -");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(statusLabel);

        panel.add(Box.createVerticalStrut(15));

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setMaximumSize(new Dimension(250, 80));
        buttonPanel.add(createButton("Calculate", e -> calculateMarks()));
        buttonPanel.add(createButton("Save", e -> saveMarks()));
        buttonPanel.add(createButton("Update", e -> updateMarks()));
        buttonPanel.add(createButton("Clear", e -> clearForm()));
        panel.add(buttonPanel);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(new Color(155, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Student", "Course", "Internal", "External", "Total", "Percentage", "Grade", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        marksTable = new JTable(tableModel);
        marksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        marksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = marksTable.getSelectedRow();
                if (row >= 0) {
                    loadMarksToForm(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(marksTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void calculateMarks() {
        try {
            if (internalField.getText().isEmpty() || externalField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both internal and external marks");
                return;
            }

            int internal = Integer.parseInt(internalField.getText());
            int external = Integer.parseInt(externalField.getText());

            if (internal < 0 || internal > 40) throw new IllegalArgumentException("Internal marks must be 0-40");
            if (external < 0 || external > 60) throw new IllegalArgumentException("External marks must be 0-60");

            Marks marks = new Marks("temp", "temp", internal, external);
            totalLabel.setText("Total: " + marks.getTotalMarks());
            percentageLabel.setText("Percentage: " + String.format("%.2f", marks.getPercentage()) + "%");
            gradeLabel.setText("Grade: " + marks.getGrade());
            statusLabel.setText("Status: " + (marks.isPassed() ? "PASSED" : "FAILED"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveMarks() {
        try {
            if (studentCombo.getSelectedItem() == null || courseCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select student and course");
                return;
            }

            int internal = Integer.parseInt(internalField.getText());
            int external = Integer.parseInt(externalField.getText());

            String[] student = studentCombo.getSelectedItem().toString().split(" - ");
            String[] course = courseCombo.getSelectedItem().toString().split(" - ");

            Marks marks = new Marks(student[0], course[0], internal, external);
            marksService.addMarks(marks);
            JOptionPane.showMessageDialog(this, "Marks saved successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMarks() {
        try {
            if (studentCombo.getSelectedItem() == null || courseCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select student and course");
                return;
            }

            int internal = Integer.parseInt(internalField.getText());
            int external = Integer.parseInt(externalField.getText());

            String[] student = studentCombo.getSelectedItem().toString().split(" - ");
            String[] course = courseCombo.getSelectedItem().toString().split(" - ");

            marksService.updateMarks(student[0], course[0], internal, external);
            JOptionPane.showMessageDialog(this, "Marks updated successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        internalField.setText("");
        externalField.setText("");
        totalLabel.setText("Total: -");
        percentageLabel.setText("Percentage: -");
        gradeLabel.setText("Grade: -");
        statusLabel.setText("Status: -");
    }

    private void loadMarksToForm(int row) {
        internalField.setText(tableModel.getValueAt(row, 2).toString());
        externalField.setText(tableModel.getValueAt(row, 3).toString());
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
        for (Marks marks : marksService.getAllMarks()) {
            tableModel.addRow(new Object[]{
                    marks.getStudentId(),
                    marks.getCourseId(),
                    marks.getInternalMarks(),
                    marks.getExternalMarks(),
                    marks.getTotalMarks(),
                    String.format("%.2f", marks.getPercentage()),
                    marks.getGrade(),
                    marks.isPassed() ? "PASSED" : "FAILED"
            });
        }
    }
}
