package com.passwordvault.core.model;

/**
 * Person 3: Password Management Core
 * 
 * Model class for categories.
 */
public class Category {
    private int id;
    private int userId;
    private String name;
    private String description;

    public Category(int userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
