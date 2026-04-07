package com.passwordvault.core.model;

import java.time.LocalDateTime;

/**
 * Person 3: Password Management Core
 * 
 * Model class for password entries.
 */
public class PasswordEntry {
    private int id;
    private int userId;
    private String website;
    private String username;
    private String passwordEncrypted;
    private int categoryId;
    private String notes;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public PasswordEntry() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public PasswordEntry(int userId, String website, String username, 
                        String passwordEncrypted, int categoryId) {
        this.userId = userId;
        this.website = website;
        this.username = username;
        this.passwordEncrypted = passwordEncrypted;
        this.categoryId = categoryId;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordEncrypted() { return passwordEncrypted; }
    public void setPasswordEncrypted(String passwordEncrypted) { this.passwordEncrypted = passwordEncrypted; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }
}
