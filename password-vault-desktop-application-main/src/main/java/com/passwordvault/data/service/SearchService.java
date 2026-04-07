package com.passwordvault.data.service;

import com.passwordvault.core.model.PasswordEntry;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Person 4: Search, Filter & Data Management
 * 
 * Provides search and filtering functionality for password entries.
 */
public class SearchService {
    
    public static List<PasswordEntry> search(List<PasswordEntry> entries, String query) {
        if (query == null || query.trim().isEmpty()) {
            return entries;
        }
        
        String lowerQuery = query.toLowerCase().trim();
        
        return entries.stream()
            .filter(entry -> 
                entry.getWebsite().toLowerCase().contains(lowerQuery) ||
                entry.getUsername().toLowerCase().contains(lowerQuery) ||
                (entry.getNotes() != null && entry.getNotes().toLowerCase().contains(lowerQuery))
            )
            .sorted((e1, e2) -> {
                int score1 = score(e1, lowerQuery);
                int score2 = score(e2, lowerQuery);
                return Integer.compare(score2, score1);
            })
            .collect(Collectors.toList());
    }
    
    private static int score(PasswordEntry entry, String query) {
        int score = 0;
        if (entry.getWebsite().toLowerCase().startsWith(query)) score += 10;
        else if (entry.getWebsite().toLowerCase().contains(query)) score += 5;
        
        if (entry.getUsername().toLowerCase().contains(query)) score += 3;
        if (entry.getNotes() != null && entry.getNotes().toLowerCase().contains(query)) score += 1;
        
        return score;
    }
}
