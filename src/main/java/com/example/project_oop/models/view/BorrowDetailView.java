package com.example.project_oop.models.view;

import java.sql.Date;

public class BorrowDetailView {
    private int id;
    private int receiptId;
    private int bookId;
    private String bookTitle;
    private Date dueDate;
    private double fineAmount;

    public BorrowDetailView(){
    }

    public BorrowDetailView(int id, int receiptId, int bookId, String bookTitle, Date dueDate, double fineAmount) {
        this.id = id;
        this.receiptId = receiptId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
        this.fineAmount = fineAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }
}
