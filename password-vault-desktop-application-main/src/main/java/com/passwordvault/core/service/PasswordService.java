package com.passwordvault.core.service;

import com.passwordvault.core.dao.PasswordEntryDAO;
import com.passwordvault.core.model.PasswordEntry;
import com.passwordvault.infrastructure.security.EncryptionManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 3: Password Management Core
 * 
 * High-level operations for password management with encryption.
 */
public class PasswordService {
    private static final Logger LOGGER = Logger.getLogger(PasswordService.class.getName());
    private final PasswordEntryDAO passwordDAO;

    public PasswordService() {
        this.passwordDAO = new PasswordEntryDAO();
    }

    public boolean addPassword(int userId, String website, String username, String plainPassword, int categoryId, String masterPassword, String salt) {
        try {
            String encrypted = EncryptionManager.encrypt(plainPassword, masterPassword, salt);
            PasswordEntry entry = new PasswordEntry(userId, website, username, encrypted, categoryId);
            return passwordDAO.create(entry);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Encryption failed during password addition", e);
            return false;
        }
    }

    public String decryptPassword(String encrypted, String masterPassword, String salt) {
        try {
            return EncryptionManager.decrypt(encrypted, masterPassword, salt);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Decryption failed", e);
            return null;
        }
    }

    public List<PasswordEntry> getPasswords(int userId) {
        return passwordDAO.getAllByUserId(userId);
    }

    public boolean deletePassword(int id) {
        return passwordDAO.delete(id);
    }
}
