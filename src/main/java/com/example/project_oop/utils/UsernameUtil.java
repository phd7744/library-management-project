package com.example.project_oop.utils;

import java.text.Normalizer;
import java.util.Locale;

public final class UsernameUtil {
    private UsernameUtil() {
    }

    public static String generateEmployeeUsername(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }

        String normalized = Normalizer.normalize(fullName.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", " ")
                .replaceAll("\\s+", " ");

        String[] parts = normalized.split(" ");
        if (parts.length == 1) {
            return parts[0].toLowerCase(Locale.ROOT);
        }

        StringBuilder username = new StringBuilder(parts[parts.length - 1].toLowerCase(Locale.ROOT));
        for (int i = 0; i < parts.length - 1; i++) {
            if (!parts[i].isBlank()) {
                username.append(Character.toLowerCase(parts[i].charAt(0)));
            }
        }

        return username.toString();
    }
}
