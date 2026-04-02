package com.example.project_oop.service;

public class LoginService {

    public boolean authenticate(LoginRole role, String username, String password) {
        if (role == null || username == null || password == null) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (normalizedUsername.isEmpty() || password.isBlank()) {
            return false;
        }

        return role.getDefaultUsername().equals(normalizedUsername)
                && role.getDefaultPassword().equals(password);
    }
}