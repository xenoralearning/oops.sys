package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * LoginFrame - The login screen for the Student Management System.
 * Demonstrates:
 * - Java Swing GUI components
 * - Event handling
 * - Simple authentication (hardcoded demo credentials)
 * 
 * Demo credentials:
 * Username: admin
 * Password: admin123
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel messageLabel;

    // Demo credentials
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin123";

    public LoginFrame() {
        setTitle("Student Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(500, 620));
        setPreferredSize(new Dimension(500, 620));

        createLoginPanel();
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(241, 245, 249));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(15, 23, 42));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(28, 24, 28, 24));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Secure access portal");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));

        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(6));
        titleContainer.add(subtitleLabel);
        headerPanel.add(titleContainer, BorderLayout.CENTER);

        JPanel loginContainer = new JPanel(new GridBagLayout());
        loginContainer.setBackground(new Color(241, 245, 249));
        loginContainer.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.weightx = 1.0;

        JLabel demoLabel = new JLabel("<html><b>Demo credentials:</b><br/>Username: admin<br/>Password: admin123</html>");
        demoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        demoLabel.setForeground(new Color(71, 85, 105));
        loginContainer.add(demoLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginContainer.add(usernameLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(260, 38));
        usernameField.setMargin(new Insets(6, 8, 6, 8));
        loginContainer.add(usernameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginContainer.add(passwordLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(260, 38));
        passwordField.setMargin(new Insets(6, 8, 6, 8));
        loginContainer.add(passwordField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        messageLabel.setForeground(new Color(185, 28, 28));
        loginContainer.add(messageLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(37, 99, 235));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this::handleLogin);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setBackground(new Color(148, 163, 184));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 0, 0);
        loginContainer.add(buttonPanel, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginContainer, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password");
            return;
        }

        if (DEMO_USERNAME.equals(username) && DEMO_PASSWORD.equals(password)) {
            messageLabel.setText("");
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open dashboard
            new DashboardFrame();
            this.dispose();
        } else {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
