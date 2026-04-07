package com.passwordvault.ui;

import com.passwordvault.auth.dao.UserDAO;
import com.passwordvault.auth.service.AuthenticationService;
import com.passwordvault.auth.session.SessionManager;
import javax.swing.*;
import java.awt.*;

/**
 * Person 5: UX/UI & Frontend Developer
 * 
 * Login screen for the application.
 */
public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final AuthenticationService authService;

    public LoginWindow() {
        this.authService = new AuthenticationService();
        initUI();
    }

    private void initUI() {
        setTitle("Password Vault - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        JLabel titleLabel = new JLabel("Welcome Back", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(0, 30, 400, 40);
        mainPanel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 100, 100, 25);
        mainPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 200, 25);
        mainPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 140, 100, 25);
        mainPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 140, 200, 25);
        mainPanel.add(passwordField);

        JButton loginBtn = DialogFactory.createStyledButton("Login", new Color(33, 150, 243));
        loginBtn.setBounds(50, 200, 140, 35);
        loginBtn.addActionListener(e -> handleLogin());
        mainPanel.add(loginBtn);

        JButton regBtn = DialogFactory.createStyledButton("Register", new Color(158, 158, 158));
        regBtn.setBounds(210, 200, 140, 35);
        regBtn.addActionListener(e -> {
            new RegistrationWindow().setVisible(true);
            this.dispose();
        });
        mainPanel.add(regBtn);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            DialogFactory.showError(this, "Username and Password are required.");
            return;
        }

        UserDAO.UserRecord user = authService.login(username, password);
        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
            new MainWindow().setVisible(true);
            this.dispose();
        } else {
            DialogFactory.showError(this, "Invalid credentials.");
        }
    }
}
