package com.passwordvault.ui;

import com.passwordvault.auth.service.AuthenticationService;
import javax.swing.*;
import java.awt.*;

/**
 * Person 5: UX/UI & Frontend Developer
 * 
 * Registration screen.
 */
public class RegistrationWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final AuthenticationService authService;

    public RegistrationWindow() {
        this.authService = new AuthenticationService();
        initUI();
    }

    private void initUI() {
        setTitle("Password Vault - Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        JLabel titleLabel = new JLabel("Join the Vault", SwingConstants.CENTER);
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

        JButton regBtn = DialogFactory.createStyledButton("Register", new Color(76, 175, 80));
        regBtn.setBounds(50, 200, 140, 35);
        regBtn.addActionListener(e -> handleRegistration());
        mainPanel.add(regBtn);

        JButton backBtn = DialogFactory.createStyledButton("Back to Login", new Color(158, 158, 158));
        backBtn.setBounds(210, 200, 140, 35);
        backBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            this.dispose();
        });
        mainPanel.add(backBtn);
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            DialogFactory.showError(this, "All fields are required.");
            return;
        }

        if (authService.register(username, password)) {
            DialogFactory.showInfo(this, "Registration successful! You can now login.");
            new LoginWindow().setVisible(true);
            this.dispose();
        } else {
            DialogFactory.showError(this, "Username already exists.");
        }
    }
}
