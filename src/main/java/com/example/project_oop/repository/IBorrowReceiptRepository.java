package com.example.project_oop.repository;

import com.example.project_oop.models.BorrowReceipt;

import java.sql.Connection;
import java.sql.SQLException;

public interface IBorrowReceiptRepository {
    void create(BorrowReceipt borrowReceipt, Connection conn) throws SQLException;
    void getById(int receiptId, Connection conn) throws SQLException;
    void getActiveByReaderId(int readerId, Connection conn) throws SQLException;
    void updateStatus(int receiptId, String status, Connection conn) throws SQLException;

}
