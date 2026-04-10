package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Employee;
import com.example.project_oop.repository.impl.EmployeeRepository;
import com.example.project_oop.utils.PasswordUtil;
import com.example.project_oop.utils.UsernameUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private static final int TEMP_PASSWORD_LENGTH = 8;
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public List<Employee> getAllEmployees() {
        return employeeRepository.get();
    }

    public Employee findByUsernameAndRole(String username, LoginRole role) throws SQLException {
        if (username == null || username.trim().isEmpty() || role == null) {
            return null;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return employeeRepository.findByUsernameAndRole(username.trim(), role.getDatabaseRole(), conn);
        }
    }

    public EmployeeLoginResult authenticate(LoginRole role, String username, String password) throws SQLException {
        if (role == null || username == null || password == null || username.trim().isEmpty() || password.isBlank()) {
            return new EmployeeLoginResult(EmployeeLoginStatus.INVALID_CREDENTIALS, null);
        }

        Employee employee = findByUsernameAndRole(username, role);
        if (employee == null) {
            return new EmployeeLoginResult(EmployeeLoginStatus.INVALID_CREDENTIALS, null);
        }

        if (employee.getStatus() != null && employee.getStatus().equalsIgnoreCase("BANNED")) {
            return new EmployeeLoginResult(EmployeeLoginStatus.BANNED, employee);
        }

        String hashedInputPassword = PasswordUtil.hashPassword(password);
        if (!hashedInputPassword.equals(employee.getPassword())) {
            return new EmployeeLoginResult(EmployeeLoginStatus.INVALID_CREDENTIALS, null);
        }

        if (employee.isFirstLogin()) {
            return new EmployeeLoginResult(EmployeeLoginStatus.REQUIRE_PASSWORD_CHANGE, employee);
        }

        return new EmployeeLoginResult(EmployeeLoginStatus.SUCCESS, employee);
    }

    public Employee registerNewEmployee(Employee employee) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            String temporaryPassword = PasswordUtil.generateTemporaryPassword(TEMP_PASSWORD_LENGTH);

            employee.setRole("LIBRARIAN");
            employee.setStatus("ACTIVE");
            employee.setFirstLogin(true);
            employee.setUsername(generateUniqueUsername(employee.getFullName(), conn));
            employee.setPassword(PasswordUtil.hashPassword(temporaryPassword));
            employeeRepository.add(employee, conn);
            conn.commit();
            employee.setPassword(temporaryPassword);
            return employee;
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Khong the tao nhan vien moi", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void changeEmployeePassword(int employeeId, String newPassword) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            employeeRepository.updatePassword(employeeId, PasswordUtil.hashPassword(newPassword), conn);
            employeeRepository.markFirstLoginCompleted(employeeId, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Khong the doi mat khau nhan vien", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private String generateUniqueUsername(String fullName, Connection conn) throws SQLException {
        String baseUsername = UsernameUtil.generateEmployeeUsername(fullName);
        if (baseUsername.isEmpty()) {
            baseUsername = "employee";
        }

        String candidate = baseUsername;
        int suffix = 1;
        while (employeeRepository.existsByUsername(candidate, conn)) {
            candidate = baseUsername + suffix;
            suffix++;
        }
        return candidate;
    }

    public enum EmployeeLoginStatus {
        SUCCESS,
        REQUIRE_PASSWORD_CHANGE,
        BANNED,
        INVALID_CREDENTIALS,
        ERROR
    }

    public static class EmployeeLoginResult {
        private final EmployeeLoginStatus status;
        private final Employee employee;

        public EmployeeLoginResult(EmployeeLoginStatus status, Employee employee) {
            this.status = status;
            this.employee = employee;
        }

        public EmployeeLoginStatus getStatus() {
            return status;
        }

        public Employee getEmployee() {
            return employee;
        }
    }
}
