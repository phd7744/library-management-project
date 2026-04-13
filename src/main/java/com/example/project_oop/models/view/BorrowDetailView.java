package com.example.project_oop.models.view;

import com.example.project_oop.models.BorrowDetail;

import java.sql.Date;

public class BorrowDetailView extends BorrowDetail {
    private int bookId;
    private String bookTitle;

    public BorrowDetailView(){
    }

    public BorrowDetailView(int bookId, String bookTitle) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
    }

    public BorrowDetailView(int id, int receiptId, int bookId, Date borrowDate, Date dueDate, Date returnDate, double fineAmount, int bookId1, String bookTitle) {
        super(id, receiptId, bookId, borrowDate, dueDate, returnDate, fineAmount);
        this.bookId = bookId1;
        this.bookTitle = bookTitle;
    }

    @Override
    public int getBookId() {
        return bookId;
    }

    @Override
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}