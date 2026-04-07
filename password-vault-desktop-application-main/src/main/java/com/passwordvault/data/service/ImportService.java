package com.passwordvault.data.service;

import com.passwordvault.core.model.PasswordEntry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 4: Search, Filter & Data Management
 * 
 * Imports user data from external files.
 */
public class ImportService {
    private static final Logger LOGGER = Logger.getLogger(ImportService.class.getName());

    public List<PasswordEntry> importFromFile(int userId, String filePath) {
        List<PasswordEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            if (!header.equals("PWV_VAULT_EXPORT_V1")) {
                throw new IOException("Invalid export file format");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                   PasswordEntry entry = new PasswordEntry(userId, parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                   entries.add(entry);
                }
            }
            return entries;
        } catch (IOException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Failed to import data from file", e);
            return null;
        }
    }
}
