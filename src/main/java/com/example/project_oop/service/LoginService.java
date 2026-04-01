package com.example.project_oop.service;

import java.util.Map;
import java.util.Optional;

public class LoginService {

    public enum Role {
        ADMIN,
        EMPLOYEE,
        READER
    }

    public record LoginResult(String username, Role role) {
    }

    private record Account(String password, Role role) {
    }

    private final Map<String, Account> accountStore = Map.of(
            "admin", new Account("admin123", Role.ADMIN),
            "employee", new Account("employee123", Role.EMPLOYEE),
            "reader", new Account("reader123", Role.READER)
    );

    public boolean authenticate(String username, String password) {
        return login(username, password).isPresent();
    }

    public Optional<LoginResult> login(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }

        String normalizedUsername = username.trim();
        if (normalizedUsername.isEmpty() || password.isBlank()) {
            return Optional.empty();
        }

        Account account = accountStore.get(normalizedUsername);
        if (account == null || !password.equals(account.password())) {
            return Optional.empty();
        }

        return Optional.of(new LoginResult(normalizedUsername, account.role()));
    }
}