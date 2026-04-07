package com.passwordvault.data.service;

import com.passwordvault.core.model.PasswordEntry;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 4: Search, Filter & Data Management
 * 
 * Exports encrypted data into a custom format.
 */
public class ExportService {
    private static final Logger LOGGER = Logger.getLogger(ExportService.class.getName());

    public String exportToFile(List<PasswordEntry> entries, String masterPassword, String salt, String filePath) {
        StringBuilder sb = new StringBuilder("PWV_VAULT_EXPORT_V1\n");
        for (PasswordEntry entry : entries) {
            sb.append(entry.getWebsite()).append("|")
              .append(entry.getUsername()).append("|")
              .append(entry.getPasswordEncrypted()).append("|")
              .append(entry.getCategoryId()).append("\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sb.toString());
            return filePath;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to export data to file", e);
            return null;
        }
    }
}
