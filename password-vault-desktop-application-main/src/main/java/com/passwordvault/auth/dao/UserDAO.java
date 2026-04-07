package com.passwordvault.auth.dao;

import com.passwordvault.infrastructure.data.BaseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Person 2: Authentication & User Management
 * 
 * Data access operations for user accounts.
 */
public class UserDAO extends BaseDAO {

    public boolean createUser(String username, String passwordHash, String salt) {
        String sql = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.setString(3, salt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating user: " + username, e);
            return false;
        }
    }

    public UserRecord getUserByUsername(String username) {
        String sql = "SELECT id, username, password_hash, salt FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserRecord(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("salt")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user: " + username, e);
        }
        return null;
    }

    public static class UserRecord {
        public final int id;
        public final String username;
        public final String passwordHash;
        public final String salt;

        public UserRecord(int id, String username, String passwordHash, String salt) {
            this.id = id;
            this.username = username;
            this.passwordHash = passwordHash;
            this.salt = salt;
        }
    }
}
