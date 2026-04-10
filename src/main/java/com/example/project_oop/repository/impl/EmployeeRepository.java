package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Employee;
import com.example.project_oop.repository.IEmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> get() {
        List<Employee> employeeList = new ArrayList<>();
        String sqlQuery = """
                SELECT emp_id, full_name, phone_number, shift, role, username, password, first_login, status
                FROM employees
                ORDER BY emp_id DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employeeList.add(new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("shift"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getBoolean("first_login")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    @Override
    public Employee findByUsernameAndRole(String username, String role, Connection conn) throws SQLException {
        String sqlQuery = """
                SELECT emp_id, full_name, phone_number, shift, role, username, password, first_login, status
                FROM employees
                WHERE username = ? AND role = ?
                LIMIT 1
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, username);
            ps.setString(2, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("shift"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status"),
                        rs.getBoolean("first_login")
                );
            }
        }
    }

    @Override
    public boolean existsByUsername(String username, Connection conn) throws SQLException {
        String sqlQuery = "SELECT 1 FROM employees WHERE username = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public void add(Employee employee, Connection conn) throws SQLException {
        String sqlQuery = """
                INSERT INTO employees (full_name, phone_number, shift, role, username, password, first_login, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, employee.getFullName());
            ps.setString(2, employee.getPhone());
            ps.setString(3, employee.getShift());
            ps.setString(4, employee.getRole());
            ps.setString(5, employee.getUsername());
            ps.setString(6, employee.getPassword());
            ps.setBoolean(7, employee.isFirstLogin());
            ps.setString(8, employee.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(int employeeId, String newPassword, Connection conn) throws SQLException {
        String sqlQuery = """
                UPDATE employees
                SET password = ?
                WHERE emp_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, newPassword);
            ps.setInt(2, employeeId);
            ps.executeUpdate();
        }
    }

    @Override
    public void markFirstLoginCompleted(int employeeId, Connection conn) throws SQLException {
        String sqlQuery = """
                UPDATE employees
                SET first_login = 0
                WHERE emp_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, employeeId);
            ps.executeUpdate();
        }
    }
}
