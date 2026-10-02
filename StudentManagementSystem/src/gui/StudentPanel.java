package gui;

import model.Student;
import service.StudentService;
import service.CourseService;
import util.ValidationUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * StudentPanel - GUI for Student Management (Add, Update, Delete, Search).
 * Demonstrates:
 * - JTable for displaying data
 * - Form input and validation
 * - CRUD operations via services
 * - Search functionality (method overloading in StudentService)
 */
public class StudentPanel extends JPanel {
    private StudentService studentService;
    private CourseService courseService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;

    // Form fields
    private JTextField idField, nameField, ageField, phoneField, emailField;
    private JTextField studentIdField, deptField, semesterField, sectionField, admissionField;

    public StudentPanel(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
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
        panel.setBackground(new Color(41, 128, 185));
        panel.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel label = new JLabel("Student Management");
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

        // Search section
        JLabel searchLabel = new JLabel("SEARCH");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(searchLabel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setBackground(new Color(236, 240, 241));
        searchTypeCombo = new JComboBox<>(new String[]{"ID", "Name"});
        searchPanel.add(searchTypeCombo);
        panel.add(searchPanel);

        searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(250, 25));
        searchField.setMaximumSize(new Dimension(250, 25));
        panel.add(searchField);

        JButton searchButton = createButton("Search", e -> performSearch());
        panel.add(searchButton);

        panel.add(Box.createVerticalStrut(15));

        // Form section
        JLabel formLabel = new JLabel("ADD/UPDATE");
        formLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(formLabel);

        idField = addField(panel, "Person ID:");
        nameField = addField(panel, "Name:");
        ageField = addField(panel, "Age:");
        phoneField = addField(panel, "Phone:");
        emailField = addField(panel, "Email:");
        studentIdField = addField(panel, "Student ID:");
        deptField = addField(panel, "Department:");
        semesterField = addField(panel, "Semester:");
        sectionField = addField(panel, "Section:");
        admissionField = addField(panel, "Admission Year:");

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setMaximumSize(new Dimension(250, 150));

        buttonPanel.add(createButton("Add", e -> addStudent()));
        buttonPanel.add(createButton("Update", e -> updateStudent()));
        buttonPanel.add(createButton("Delete", e -> deleteStudent()));
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
        field.setPreferredSize(new Dimension(250, 22));
        field.setMaximumSize(new Dimension(250, 22));
        panel.add(field);

        return field;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Name", "Age", "Phone", "Email", "Student ID", "Dept", "Sem", "Sec", "Admission"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = studentTable.getSelectedRow();
                if (row >= 0) {
                    loadStudentToForm(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void performSearch() {
        String searchTerm = searchField.getText();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }

        tableModel.setRowCount(0);
        java.util.List<Student> results;

        if ("ID".equals(searchTypeCombo.getSelectedItem())) {
            if (!ValidationUtil.isValidInteger(searchTerm)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ID number");
                return;
            }
            results = studentService.searchStudent(Integer.parseInt(searchTerm));
        } else {
            results = studentService.searchStudent(searchTerm);
        }

        for (Student student : results) {
            addStudentToTable(student);
        }
    }

    private void addStudent() {
        try {
            validateForm();
            Student student = new Student(
                    0, // ID auto-generated
                    nameField.getText().trim(),
                    Integer.parseInt(ageField.getText()),
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    studentIdField.getText().trim(),
                    deptField.getText().trim(),
                    Integer.parseInt(semesterField.getText()),
                    sectionField.getText().trim(),
                    Integer.parseInt(admissionField.getText())
            );
            studentService.addStudent(student);
            JOptionPane.showMessageDialog(this, "Student added successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a student to update");
                return;
            }
            validateForm();
            Student student = new Student(
                    Integer.parseInt(idField.getText()),
                    nameField.getText().trim(),
                    Integer.parseInt(ageField.getText()),
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    studentIdField.getText().trim(),
                    deptField.getText().trim(),
                    Integer.parseInt(semesterField.getText()),
                    sectionField.getText().trim(),
                    Integer.parseInt(admissionField.getText())
            );
            studentService.updateStudent(student);
            JOptionPane.showMessageDialog(this, "Student updated successfully");
            clearForm();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        try {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a student to delete");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(idField.getText());
                studentService.deleteStudent(id);
                JOptionPane.showMessageDialog(this, "Student deleted successfully");
                clearForm();
                refreshTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateForm() throws IllegalArgumentException {
        if (!ValidationUtil.isNotEmpty(nameField.getText())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!ValidationUtil.isValidInteger(ageField.getText())) {
            throw new IllegalArgumentException("Age must be a number");
        }
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            throw new IllegalArgumentException("Phone must be 10 digits");
        }
        if (!ValidationUtil.isValidEmail(emailField.getText())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ValidationUtil.isNotEmpty(studentIdField.getText())) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        emailField.setText("");
        studentIdField.setText("");
        deptField.setText("");
        semesterField.setText("");
        sectionField.setText("");
        admissionField.setText("");
        searchField.setText("");
    }

    private void loadStudentToForm(int row) {
        idField.setText(tableModel.getValueAt(row, 0).toString());
        nameField.setText(tableModel.getValueAt(row, 1).toString());
        ageField.setText(tableModel.getValueAt(row, 2).toString());
        phoneField.setText(tableModel.getValueAt(row, 3).toString());
        emailField.setText(tableModel.getValueAt(row, 4).toString());
        studentIdField.setText(tableModel.getValueAt(row, 5).toString());
        deptField.setText(tableModel.getValueAt(row, 6).toString());
        semesterField.setText(tableModel.getValueAt(row, 7).toString());
        sectionField.setText(tableModel.getValueAt(row, 8).toString());
        admissionField.setText(tableModel.getValueAt(row, 9).toString());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student student : studentService.getAllStudents()) {
            addStudentToTable(student);
        }
    }

    private void addStudentToTable(Student student) {
        tableModel.addRow(new Object[]{
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getPhone(),
                student.getEmail(),
                student.getStudentId(),
                student.getDepartment(),
                student.getSemester(),
                student.getSection(),
                student.getAdmissionYear()
        });
    }
}
