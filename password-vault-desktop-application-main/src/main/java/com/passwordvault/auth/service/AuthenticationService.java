package com.passwordvault.auth.service;

import com.passwordvault.auth.dao.UserDAO;
import com.passwordvault.auth.security.BcryptPasswordEncoder;
import com.passwordvault.infrastructure.security.EncryptionManager;
import java.util.logging.Logger;

/**
 * Person 2: Authentication & User Management
 * 
 * Main service for login and registration flows.
 */
public class AuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    private final UserDAO userDAO;

    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }

    public boolean register(String username, String password) {
        if (userDAO.getUserByUsername(username) != null) {
            LOGGER.warning("Registration failed: Username already exists -> " + username);
            return false;
        }
        
        String salt = EncryptionManager.generateSalt();
        String hash = BcryptPasswordEncoder.encode(password);
        
        return userDAO.createUser(username, hash, salt);
    }

    public UserDAO.UserRecord login(String username, String password) {
        UserDAO.UserRecord user = userDAO.getUserByUsername(username);
        if (user == null) {
            LOGGER.warning("Login failed: User not found -> " + username);
            return null;
        }

        if (BcryptPasswordEncoder.matches(password, user.passwordHash)) {
            LOGGER.info("Login successful for user: " + username);
            return user;
        } else {
            LOGGER.warning("Login failed: Incorrect password for user: " + username);
            return null;
        }
    }
}
