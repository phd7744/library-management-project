package com.example.project_oop.models;

public class Employee {
    private int id;
    private String fullName;
    private String phone;
    private String shift;
    private String role;
    private String username;
    private String password;
    private String status;
    private boolean firstLogin;

    public Employee() {
    }

    public Employee(int id, String fullName, String phone, String shift, String role,
                    String username, String password, String status, boolean firstLogin) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.shift = shift;
        this.role = role;
        this.username = username;
        this.password = password;
        this.status = status;
        this.firstLogin = firstLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
