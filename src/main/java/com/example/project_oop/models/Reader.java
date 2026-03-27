package com.example.project_oop.models;

public class Reader {
    private int id;
    private String fullname;
    private String phone;
    private String type;
    private double debt;

    public Reader(){}

    public Reader(int id, String fullname, String phone, String type, double debt){
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.type = type;
        this.debt = debt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }
}

