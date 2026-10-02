package gui;

import model.Course;
import service.CourseService;
import util.ValidationUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * CoursePanel - GUI for Course Management
 */
public class CoursePanel extends JPanel {
    private CourseService courseService;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField courseIdField, courseNameField, creditsField, semesterField, deptField;

    public CoursePanel(CourseService courseService) {
        this.courseService = courseService;
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);

        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(46, 204, 113));
        panel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel label = new JLabel("Course Management");
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

        JLabel searchLabel = new JLabel("SEARCH");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(searchLabel);
        searchField = new JTextField(15);
        searchField.setMaximumSize(new Dimension(250, 25));
        panel.add(searchField);
        JButton searchBtn = createButton("Search", e -> performSearch());
        panel.add(searchBtn);
        panel.add(Box.createVerticalStrut(15));

        JLabel formLabel = new JLabel("ADD/UPDATE");
        formLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(formLabel);

        courseIdField = addField(panel, "Course ID:");
        courseNameField = addField(panel, "Course Name:");
        creditsField = addField(panel, "Credits:");
        semesterField = addField(panel, "Semester:");
        deptField = addField(panel, "Department:");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setMaximumSize(new Dimension(250, 150));
        buttonPanel.add(createButton("Add", e -> addCourse()));
        buttonPanel.add(createButton("Update", e -> updateCourse()));
        buttonPanel.add(createButton("Delete", e -> deleteCourse()));
        buttonPanel.add(createButton("Clear", e -> clearForm()));

        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JTextField addField(JPanel panel, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 10));
        lbl.setMaximumSize(new Dimension(250, 15));
        panel.add(lbl);
        JTextField field = new JTextField(15);
        field.setMaximumSize(new Dimension(250, 22));
        panel.add(field);
        return field;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Course ID", "Course Name", "Credits", "Semester", "Department"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = courseTable.getSelectedRow();
                if (row >= 0) {
                    loadCourseToForm(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(courseTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void performSearch() {
        String searchTerm = searchField.getText();
        tableModel.setRowCount(0);
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }

        for (Course course : courseService.searchByCourseName(searchTerm)) {
            addCourseToTable(course);
        }
    }

    private void addCourse() {
        try {
            if (!ValidationUtil.isNotEmpty(courseIdField.getText())) throw new IllegalArgumentException("Course ID cannot be empty");
            if (!ValidationUtil.isNotEmpty(courseNameField.getText())) throw new IllegalArgumentException("Course name cannot be empty");
            if (!ValidationUtil.isValidCredits(Integer.parseInt(creditsField.getText()))) throw new IllegalArgumentException("Invalid credits");

            Course course = new Course(
                    courseIdField.getText().trim(),
                    courseNameField.getText().trim(),
                    Integer.parseInt(creditsField.getText()),
                    Integer.parseInt(semesterField.getText()),
                    deptField.getText().trim()
            );
            courseService.addCourse(course);
            JOptionPane.showMessageDialog(this, "Course added successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourse() {
        try {
            if (courseIdField.getText().isEmpty()) throw new IllegalArgumentException("Select a course to update");
            Course course = new Course(
                    courseIdField.getText().trim(),
                    courseNameField.getText().trim(),
                    Integer.parseInt(creditsField.getText()),
                    Integer.parseInt(semesterField.getText()),
                    deptField.getText().trim()
            );
            courseService.updateCourse(course);
            JOptionPane.showMessageDialog(this, "Course updated successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourse() {
        try {
            if (courseIdField.getText().isEmpty()) throw new IllegalArgumentException("Select a course to delete");
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this course?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                courseService.deleteCourse(courseIdField.getText());
                JOptionPane.showMessageDialog(this, "Course deleted successfully");
                clearForm();
                refreshTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        courseIdField.setText("");
        courseNameField.setText("");
        creditsField.setText("");
        semesterField.setText("");
        deptField.setText("");
        searchField.setText("");
    }

    private void loadCourseToForm(int row) {
        courseIdField.setText(tableModel.getValueAt(row, 0).toString());
        courseNameField.setText(tableModel.getValueAt(row, 1).toString());
        creditsField.setText(tableModel.getValueAt(row, 2).toString());
        semesterField.setText(tableModel.getValueAt(row, 3).toString());
        deptField.setText(tableModel.getValueAt(row, 4).toString());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Course course : courseService.getAllCourses()) {
            addCourseToTable(course);
        }
    }

    private void addCourseToTable(Course course) {
        tableModel.addRow(new Object[]{
                course.getCourseId(),
                course.getCourseName(),
                course.getCredits(),
                course.getSemester(),
                course.getDepartment()
        });
    }
}
