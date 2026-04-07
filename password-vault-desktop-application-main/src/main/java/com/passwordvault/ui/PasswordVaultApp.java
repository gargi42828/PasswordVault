package com.passwordvault.ui;

import com.passwordvault.infrastructure.db.DatabaseManager;
import javax.swing.*;
import java.util.logging.Logger;

/**
 * Person 5: UX/UI & Frontend Developer
 * 
 * Application Entry Point.
 */
public class PasswordVaultApp {
    private static final Logger LOGGER = Logger.getLogger(PasswordVaultApp.class.getName());

    public static void main(String[] args) {
        // Initialize database foundation (Person 1)
        try {
            DatabaseManager.getInstance().initialize();
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize database: " + e.getMessage());
            System.exit(1);
        }

        // Set Look and Feel (Person 5)
        /* Commented out to allow custom button colors to be respected
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.warning("Could not set system look and feel.");
        }
        */

        // Start Login Flow
        SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
