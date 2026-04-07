package com.passwordvault.data.util;

import com.passwordvault.core.model.PasswordEntry;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Person 4: Search, Filter & Data Management
 * 
 * Advanced filtering with multiple criteria and dynamic sorting.
 */
public class FilterBuilder {
    
    public static List<PasswordEntry> filter(List<PasswordEntry> entries, Integer categoryId, String sortBy) {
        Stream<PasswordEntry> stream = entries.stream();
        
        if (categoryId != null && categoryId > 0) {
            stream = stream.filter(e -> e.getCategoryId() == categoryId);
        }
        
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "website":
                    stream = stream.sorted(Comparator.comparing(PasswordEntry::getWebsite, String.CASE_INSENSITIVE_ORDER));
                    break;
                case "username":
                    stream = stream.sorted(Comparator.comparing(PasswordEntry::getUsername, String.CASE_INSENSITIVE_ORDER));
                    break;
                case "date":
                    stream = stream.sorted(Comparator.comparing(PasswordEntry::getCreatedDate).reversed());
                    break;
            }
        }
        
        return stream.collect(Collectors.toList());
    }
}
