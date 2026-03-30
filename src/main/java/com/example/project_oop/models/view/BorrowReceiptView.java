package com.example.project_oop.models.view;

import java.sql.Date;

public class BorrowReceiptView {
    private int receiptId;
    private String readerName;
    private int totalBooks;    
    private Date borrowDate;
    private Date dueDate;
    private String status;

    public BorrowReceiptView() {
    }

    public BorrowReceiptView(int receiptId, String readerName, int totalBooks, Date borrowDate, Date dueDate, String status) {
        this.receiptId = receiptId;
        this.readerName = readerName;
        this.totalBooks = totalBooks;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
