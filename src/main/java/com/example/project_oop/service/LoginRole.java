package com.example.project_oop.service;

public enum LoginRole {
    ADMIN("Admin", "ADMIN"),
    EMPLOYEE("Employee", "LIBRARIAN");

    private final String displayName;
    private final String databaseRole;

    LoginRole(String displayName, String databaseRole) {
        this.displayName = displayName;
        this.databaseRole = databaseRole;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDatabaseRole() {
        return databaseRole;
    }
}