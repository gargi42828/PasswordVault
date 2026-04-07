package com.passwordvault.auth.session;

import com.passwordvault.auth.dao.UserDAO;
import java.util.logging.Logger;

/**
 * Person 2: Authentication & User Management
 * 
 * Manages the current user session for the application.
 */
public class SessionManager {
    private static final Logger LOGGER = Logger.getLogger(SessionManager.class.getName());
    private static SessionManager instance;
    private UserDAO.UserRecord currentUser;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(UserDAO.UserRecord user) {
        this.currentUser = user;
        if (user != null) {
            LOGGER.info("Session started for user: " + user.username);
        } else {
            LOGGER.info("Session terminated.");
        }
    }

    public UserDAO.UserRecord getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        setCurrentUser(null);
    }
}
