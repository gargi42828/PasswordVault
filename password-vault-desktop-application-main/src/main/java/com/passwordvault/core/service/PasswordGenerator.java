package com.passwordvault.core.service;

import java.security.SecureRandom;

/**
 * Person 3: Password Management Core
 * 
 * Generates secure, random passwords based on user preferences.
 */
public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_+={}[]|;:,.<>?";

    public static String generate(int length, boolean useUpper, boolean useDigits, boolean useSpecial) {
        StringBuilder charset = new StringBuilder(LOWER);
        if (useUpper) charset.append(UPPER);
        if (useDigits) charset.append(DIGITS);
        if (useSpecial) charset.append(SPECIAL);

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            password.append(charset.charAt(index));
        }
        return password.toString();
    }
}
