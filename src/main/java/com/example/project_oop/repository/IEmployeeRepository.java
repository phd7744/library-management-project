package com.example.project_oop.repository;

import com.example.project_oop.models.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IEmployeeRepository {
    List<Employee> get();
    Employee findByUsernameAndRole(String username, String role, Connection conn) throws SQLException;
    boolean existsByUsername(String username, Connection conn) throws SQLException;
    void add(Employee employee, Connection conn) throws SQLException;
    void updatePassword(int employeeId, String newPassword, Connection conn) throws SQLException;
    void markFirstLoginCompleted(int employeeId, Connection conn) throws SQLException;
    void update(Employee employee, Connection conn) throws SQLException;
    void delete(int employeeId, Connection conn) throws SQLException;
}
