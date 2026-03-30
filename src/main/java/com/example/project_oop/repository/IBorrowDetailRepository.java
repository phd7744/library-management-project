package com.example.project_oop.repository;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.view.BorrowDetailView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public interface IBorrowDetailRepository {
    void add(BorrowDetail detail, Connection conn) throws SQLException;
    void returnBook(int detailId, Date returnDate, double fineAmount, Connection conn) throws SQLException;
    List<BorrowDetailView> getByReceiptId(int receiptId) throws SQLException;
}
