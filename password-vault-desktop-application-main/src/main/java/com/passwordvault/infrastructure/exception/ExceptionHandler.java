package com.passwordvault.infrastructure.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Person 1: Infrastructure Architect & Database Designer
 * 
 * Centralized exception handling framework.
 */
public class ExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());

    public static void handle(Throwable e, String contextMessage) {
        LOGGER.log(Level.SEVERE, contextMessage + ": " + e.getMessage(), e);
        // Optionally show a dialog for the GUI
    }

    public static class VaultException extends RuntimeException {
        public VaultException(String message) {
            super(message);
        }
    }

    public static class CryptoException extends VaultException {
        public CryptoException(String message) {
            super(message);
        }
    }

    public static class DatabaseException extends VaultException {
        public DatabaseException(String message, Throwable cause) {
            super(message + (cause != null ? ": " + cause.getMessage() : ""));
        }
    }
}
