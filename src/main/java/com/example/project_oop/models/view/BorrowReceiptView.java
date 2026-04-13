package com.example.project_oop.models.view;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.BorrowReceipt;

import java.util.Date;

public class BorrowReceiptView extends BorrowReceipt {
    private String readerName;
    private int totalBooks;

    public BorrowReceiptView() {
    }


    public BorrowReceiptView(int id, int readerId, int empId, Date borrowDate, String status, String readerName, int totalBooks) {
        super(id, readerId, empId, borrowDate, status);
        this.readerName = readerName;
        this.totalBooks = totalBooks;
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
}