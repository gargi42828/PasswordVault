package com.passwordvault.auth.logging;

import com.passwordvault.auth.session.SessionManager;
import com.passwordvault.infrastructure.db.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 2: Authentication & User Management
 * 
 * Logs security audit events directly into the database.
 */
public class AuditLogger {
    private static final Logger LOGGER = Logger.getLogger(AuditLogger.class.getName());

    public static void log(String action) {
        int userId = SessionManager.getInstance().isLoggedIn() ? SessionManager.getInstance().getCurrentUser().id : -1;
        String sql = "INSERT INTO audit_logs (user_id, action) VALUES (?, ?)";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.executeUpdate();
            LOGGER.info("Audit Log: " + action + " for user_id=" + userId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to log audit event", e);
        }
    }
}
