package com.example.project_oop.service;

public final class LoginSession {

    private static String currentUsername;
    private static LoginRole currentRole;

    private LoginSession() {
    }

    public static void setCurrentUser(String username, LoginRole role) {
        currentUsername = username;
        currentRole = role;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static LoginRole getCurrentRole() {
        return currentRole;
    }

    public static void clear() {
        currentUsername = null;
        currentRole = null;
    }
}