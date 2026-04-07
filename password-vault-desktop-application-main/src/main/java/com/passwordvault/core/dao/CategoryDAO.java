package com.passwordvault.core.dao;

import com.passwordvault.core.model.Category;
import com.passwordvault.infrastructure.data.BaseDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Person 3: Password Management Core
 * 
 * DAO for category persistence.
 */
public class CategoryDAO extends BaseDAO {

    public boolean create(Category category) {
        String sql = "INSERT INTO categories (user_id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, category.getUserId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getDescription());
            
            if (stmt.executeUpdate() > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        category.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating category", e);
        }
        return false;
    }

    public List<Category> getByUserId(int userId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category cat = new Category(rs.getInt("user_id"), rs.getString("name"), rs.getString("description"));
                    cat.setId(rs.getInt("id"));
                    categories.add(cat);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching categories", e);
        }
        return categories;
    }
}
