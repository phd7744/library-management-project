package com.example.project_oop.models;

public class Reader {
    private int id;
    private String fullName;
    private String phone;
    private double debt;
    private boolean firstLogin;
    private String status;
    private String username;
    private String password;

    public Reader() {
    }

    public Reader(int id, String fullName, String phone, double debt, String status, String username, String password) {
        this(id, fullName, phone, debt, false, status, username, password);
    }

    public Reader(int id, String fullName, String phone, double debt, boolean firstLogin, String status, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.debt = debt;
        this.firstLogin = firstLogin;
        this.status = status;
        this.username = username;
        this.password = password;
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

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getStatus(){return status;}

    public void setStatus(String status){this.status = status;}

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
}

