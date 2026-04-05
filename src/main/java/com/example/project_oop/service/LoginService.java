package com.example.project_oop.service;

import com.example.project_oop.models.Reader;

import java.sql.SQLException;
import java.util.Locale;

public class LoginService {

    private final ReaderService readerService = new ReaderService();

    public enum CustomerLoginStatus {
        SUCCESS,
        REQUIRE_PASSWORD_CHANGE,
        BANNED,
        INVALID_CREDENTIALS,
        ERROR
    }

    public static class CustomerLoginResult {
        private final CustomerLoginStatus status;
        private final Reader reader;

        public CustomerLoginResult(CustomerLoginStatus status, Reader reader) {
            this.status = status;
            this.reader = reader;
        }

        public CustomerLoginStatus getStatus() {
            return status;
        }

        public Reader getReader() {
            return reader;
        }
    }

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

    public CustomerLoginResult authenticateCustomer(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.isBlank()) {
            return new CustomerLoginResult(CustomerLoginStatus.INVALID_CREDENTIALS, null);
        }

        try {
            Reader reader = readerService.findByUsername(username.trim());
            if (reader == null) {
                return new CustomerLoginResult(CustomerLoginStatus.INVALID_CREDENTIALS, null);
            }

            if (isBannedStatus(reader.getStatus())) {
                return new CustomerLoginResult(CustomerLoginStatus.BANNED, reader);
            }

            String hashedInputPassword = readerService.hashPassword(password);
            if (!hashedInputPassword.equals(reader.getPassword())) {
                return new CustomerLoginResult(CustomerLoginStatus.INVALID_CREDENTIALS, null);
            }

            if (isFirstLogin(reader)) {
                return new CustomerLoginResult(CustomerLoginStatus.REQUIRE_PASSWORD_CHANGE, reader);
            }

            return new CustomerLoginResult(CustomerLoginStatus.SUCCESS, reader);
        } catch (SQLException e) {
            return new CustomerLoginResult(CustomerLoginStatus.ERROR, null);
        }
    }

    private boolean isBannedStatus(String status) {
        if (status == null) {
            return false;
        }

        String normalized = status.trim().toUpperCase(Locale.ROOT);
        return "BANNED".equals(normalized) || "BANED".equals(normalized);
    }

    private boolean isFirstLogin(Reader reader) {
        if (reader == null) {
            return false;
        }

        return reader.isFirstLogin();
    }
}