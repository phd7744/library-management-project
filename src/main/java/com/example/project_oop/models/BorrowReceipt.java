package com.example.project_oop.models;

import java.util.Date;

public class BorrowReceipt {
    private int id;
    private int readerId;
    private int empId;
    private Date borrowDate;
    private String status;

    public BorrowReceipt() {
    }

    public BorrowReceipt(int id, int readerId, int empId, Date borrowDate, String status) {
        this.id = id;
        this.readerId = readerId;
        this.empId = empId;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
