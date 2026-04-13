package com.example.project_oop.service;

public final class LoginSession {

    private static String currentUsername;
    private static LoginRole currentRole;
    private static Integer currentEmployeeId;
    private static String currentFullName;

    private LoginSession() {
    }

    public static void setCurrentUser(String username, LoginRole role) {
        setCurrentUser(username, role, null, null);
    }

    public static void setCurrentUser(String username, LoginRole role, Integer employeeId) {
        setCurrentUser(username, role, employeeId, null);
    }

    public static void setCurrentUser(String username, LoginRole role, Integer employeeId, String fullName) {
        currentUsername = username;
        currentRole = role;
        currentEmployeeId = employeeId;
        currentFullName = fullName;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static LoginRole getCurrentRole() {
        return currentRole;
    }

    public static Integer getCurrentEmployeeId() {
        return currentEmployeeId;
    }

    public static String getCurrentFullName() {
        return currentFullName;
    }

    public static void clear() {
        currentUsername = null;
        currentRole = null;
        currentEmployeeId = null;
        currentFullName = null;
    }
}