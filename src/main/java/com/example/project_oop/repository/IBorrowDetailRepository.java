package com.example.project_oop.repository;

import com.example.project_oop.models.BorrowDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IBorrowDetailRepository {
    void returnBook(BorrowDetail bd, Connection conn) throws SQLException;
    List<BorrowDetail> getByReceiptId(int receiptId) throws SQLException;
}