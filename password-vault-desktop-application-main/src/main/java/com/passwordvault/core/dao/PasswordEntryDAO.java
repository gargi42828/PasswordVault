package com.passwordvault.core.dao;

import com.passwordvault.core.model.PasswordEntry;
import com.passwordvault.infrastructure.data.BaseDAO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Person 3: Password Management Core
 * 
 * DAO for password entry persistence.
 */
public class PasswordEntryDAO extends BaseDAO {

    public boolean create(PasswordEntry entry) {
        String sql = "INSERT INTO password_entries (user_id, website, username, password_encrypted, category_id, notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entry.getUserId());
            stmt.setString(2, entry.getWebsite());
            stmt.setString(3, entry.getUsername());
            stmt.setString(4, entry.getPasswordEncrypted());
            stmt.setInt(5, entry.getCategoryId());
            stmt.setString(6, entry.getNotes());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entry.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating password entry", e);
        }
        return false;
    }

    public List<PasswordEntry> getAllByUserId(int userId) {
        List<PasswordEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM password_entries WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PasswordEntry entry = new PasswordEntry();
                    entry.setId(rs.getInt("id"));
                    entry.setUserId(rs.getInt("user_id"));
                    entry.setWebsite(rs.getString("website"));
                    entry.setUsername(rs.getString("username"));
                    entry.setPasswordEncrypted(rs.getString("password_encrypted"));
                    entry.setCategoryId(rs.getInt("category_id"));
                    entry.setNotes(rs.getString("notes"));
                    entry.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                    entry.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
                    entries.add(entry);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching password entries", e);
        }
        return entries;
    }

    public boolean update(PasswordEntry entry) {
        String sql = "UPDATE password_entries SET website = ?, username = ?, password_encrypted = ?, category_id = ?, notes = ?, updated_date = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entry.getWebsite());
            stmt.setString(2, entry.getUsername());
            stmt.setString(3, entry.getPasswordEncrypted());
            stmt.setInt(4, entry.getCategoryId());
            stmt.setString(5, entry.getNotes());
            stmt.setInt(6, entry.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating password entry", e);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM password_entries WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting password entry", e);
        }
        return false;
    }
}
