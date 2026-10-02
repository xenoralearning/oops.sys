package gui;

import service.*;
import data.AppSession;
import javax.swing.*;
import java.awt.*;

/**
 * DashboardFrame - Main dashboard for the Student Management System.
 * Demonstrates:
 * - Swing GUI with CardLayout for switching between panels
 * - Navigation/sidebar layout
 * - Integration of all services
 * - Dashboard statistics
 */
public class DashboardFrame extends JFrame {
    private StudentService studentService;
    private CourseService courseService;
    private MarksService marksService;
    private AttendanceService attendanceService;

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JLabel statsStudentsLabel;
    private JLabel statsCoursesLabel;
    private JLabel statsAttendanceLabel;

    public DashboardFrame() {
        setTitle("Student Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setResizable(true);

        AppSession.initialize();
        studentService = AppSession.getStudentService();
        courseService = AppSession.getCourseService();
        marksService = AppSession.getMarksService();
        attendanceService = AppSession.getAttendanceService();

        createUI();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar (Navigation)
        JPanel sidebarPanel = createSidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        // Content area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add different panels
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(new StudentPanel(studentService, courseService), "Students");
        contentPanel.add(new CoursePanel(courseService), "Courses");
        contentPanel.add(new MarksPanel(studentService, courseService, marksService), "Marks");
        contentPanel.add(new AttendancePanel(studentService, courseService, attendanceService), "Attendance");
        contentPanel.add(new ReportPanel(studentService, marksService, attendanceService), "Reports");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(15, 23, 42));
        sidebar.setPreferredSize(new Dimension(230, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 16, 16, 16));

        JLabel titleLabel = new JLabel("SMS");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(148, 163, 184));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(18));

        JLabel subTitle = new JLabel("Academic Suite");
        subTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subTitle.setForeground(new Color(148, 163, 184));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(subTitle);
        sidebar.add(Box.createVerticalStrut(22));

        String[] menuItems = {"Dashboard", "Students", "Courses", "Marks", "Attendance", "Reports"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(190, 42));
            button.setFont(new Font("SansSerif", Font.BOLD, 13));
            button.setBackground(new Color(30, 41, 59));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            button.addActionListener(e -> {
                cardLayout.show(contentPanel, item);
                updateDashboardStats();
            });

            sidebar.add(button);
            sidebar.add(Box.createVerticalStrut(8));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(190, 42));
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        logoutButton.setBackground(new Color(220, 38, 38));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        logoutButton.addActionListener(e -> {
            new LoginFrame();
            this.dispose();
        });
        sidebar.add(logoutButton);
        sidebar.add(Box.createVerticalStrut(10));

        return sidebar;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout(20, 20));
        dashboardPanel.setBackground(new Color(241, 245, 249));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(15, 118, 110));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        JLabel headerLabel = new JLabel("Dashboard");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        statsPanel.add(createStatsCard("Total Students", "0", new Color(59, 130, 246)));
        statsPanel.add(createStatsCard("Total Courses", "0", new Color(16, 185, 129)));
        statsPanel.add(createStatsCard("Avg. Attendance", "0%", new Color(245, 158, 11)));

        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(statsPanel, BorderLayout.CENTER);

        updateDashboardStats();
        return dashboardPanel;
    }

    private JPanel createStatsCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLabel.setForeground(new Color(71, 85, 105));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        if (title.contains("Students")) {
            statsStudentsLabel = valueLabel;
        } else if (title.contains("Courses")) {
            statsCoursesLabel = valueLabel;
        } else if (title.contains("Attendance")) {
            statsAttendanceLabel = valueLabel;
        }

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void updateDashboardStats() {
        if (statsStudentsLabel != null) {
            statsStudentsLabel.setText(String.valueOf(studentService.getTotalStudents()));
        }
        if (statsCoursesLabel != null) {
            statsCoursesLabel.setText(String.valueOf(courseService.getTotalCourses()));
        }
        if (statsAttendanceLabel != null) {
            try {
                double avgAttendance = calculateAverageAttendance();
                statsAttendanceLabel.setText(String.format("%.1f%%", avgAttendance));
            } catch (Exception e) {
                statsAttendanceLabel.setText("N/A");
            }
        }
    }

    private double calculateAverageAttendance() {
        try {
            var allAttendances = attendanceService.getAllAttendance();
            if (allAttendances.isEmpty()) return 0;
            
            double total = 0;
            for (var att : allAttendances) {
                total += att.calculatePercentage();
            }
            return total / allAttendances.size();
        } catch (Exception e) {
            return 0;
        }
    }
}
