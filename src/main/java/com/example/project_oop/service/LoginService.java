package com.example.project_oop.service;

import java.util.Map;

public class LoginService {

    private final Map<String, String> accountStore = Map.of(
            "admin", "admin123",
            "staff", "staff123"
    );

    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (normalizedUsername.isEmpty() || password.isBlank()) {
            return false;
        }

        String expectedPassword = accountStore.get(normalizedUsername);
        return password.equals(expectedPassword);
    }
}
