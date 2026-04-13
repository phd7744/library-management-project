package com.example.project_oop.models;

import java.util.Date;

public class BorrowReceipt {
    private int receiptId;
    private int readerId;
    private int empId;
    private Date borrowDate;
    private String status;

    public BorrowReceipt() {
    }

    public BorrowReceipt(int receiptId, int readerId, int empId, Date borrowDate, String status) {
        this.receiptId = receiptId;
        this.readerId = readerId;
        this.empId = empId;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
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
