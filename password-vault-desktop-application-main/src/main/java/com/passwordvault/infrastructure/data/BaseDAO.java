package com.passwordvault.infrastructure.data;

import com.passwordvault.infrastructure.db.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 1: Infrastructure Architect & Database Designer
 * 
 * Abstract BaseDAO for consistent data access operations.
 */
public abstract class BaseDAO {
    protected static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            // Connection management is handled via the singleton, but closing is safe if we want to return to pool
            // DatabaseManager's getConnection may be returning a pooled connection
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing database resources", e);
        }
    }
}
