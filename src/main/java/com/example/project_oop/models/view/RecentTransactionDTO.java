package com.example.project_oop.models.view;

import java.util.Date;

public class RecentTransactionDTO {
    private int receiptId;
    private String readerName;
    private String bookTitle;
    private Date borrowDate;
    private String status;

    public RecentTransactionDTO() {
    }

    public RecentTransactionDTO(int receiptId, String readerName, String bookTitle, Date borrowDate, String status) {
        this.receiptId = receiptId;
        this.readerName = readerName;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
