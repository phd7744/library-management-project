package com.example.project_oop.service;

public enum LoginRole {
    ADMIN("Admin", "admin", "admin"),
    EMPLOYEE("Employee", "employee", "employee");

    private final String displayName;
    private final String defaultUsername;
    private final String defaultPassword;

    LoginRole(String displayName, String defaultUsername, String defaultPassword) {
        this.displayName = displayName;
        this.defaultUsername = defaultUsername;
        this.defaultPassword = defaultPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }
}