package com.passwordvault.auth.util;

/**
 * Person 2: Authentication & User Management
 * 
 * Validates password strength and format.
 */
public class PasswordValidator {
    public enum PasswordStrength {
        WEAK, FAIR, GOOD, STRONG
    }

    public static PasswordStrength validate(String password) {
        int score = 0;
        if (password.length() >= 8) score += 20;
        if (password.length() >= 12) score += 10;
        if (password.matches(".*[a-z].*")) score += 15;
        if (password.matches(".*[A-Z].*")) score += 15;
        if (password.matches(".*[0-9].*")) score += 15;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) score += 25;
        
        if (score < 30) return PasswordStrength.WEAK;
        if (score < 50) return PasswordStrength.FAIR;
        if (score < 70) return PasswordStrength.GOOD;
        return PasswordStrength.STRONG;
    }
}
