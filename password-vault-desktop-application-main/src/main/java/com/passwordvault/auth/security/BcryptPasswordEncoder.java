package com.passwordvault.auth.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Person 2: Authentication & User Management
 * 
 * Handles password hashing and verification using BCrypt.
 */
public class BcryptPasswordEncoder {
    private static final int BCRYPT_STRENGTH = 12;

    public static String encode(String password) {
        return BCrypt.withDefaults().hashToString(BCRYPT_STRENGTH, password.toCharArray());
    }

    public static boolean matches(String password, String encodedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword).verified;
    }
}
