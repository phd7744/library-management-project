package com.example.project_oop.repository.impl;

import com.example.project_oop.models.BorrowReceipt;
import com.example.project_oop.repository.IBorrowReceiptRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorrowReceiptRepository implements IBorrowReceiptRepository {
    @Override
    public void create(BorrowReceipt borrowReceipt, Connection conn) throws SQLException {
        String sqlQuery = """
                INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status)
                VALUES (?,?,?,?)
                """;
        try(PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1,borrowReceipt.getReaderId());
            ps.setInt(2,borrowReceipt.getEmpId());
            ps.setDate(3, (Date) borrowReceipt.getBorrowDate());
            ps.setString(4,borrowReceipt.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void getById(int receiptId, Connection conn) throws SQLException {
        String sqlQuery = """
                SELECT * FROM borrow_receipts as br
                WHERE br.receipt_id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sqlQuery))  {
            ps.setInt(1,receiptId);
            ps.executeUpdate();
        }
    }

    @Override
    public void getActiveByReaderId(int reader_id, Connection conn) throws SQLException {
        String sqlQuery = """
                SELECT br.status FROM borrow_receipts as br
                WHERE br.reader_id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sqlQuery))  {
            ps.setInt(1,reader_id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateStatus(int receiptId, String status, Connection conn) throws SQLException {
        String sqlQuery = """
                UPDATE borrow_receipts
                SET status = ?
                WHERE borrow_receipts.receipt_id = ?
                """;
        try(PreparedStatement ps = conn.prepareStatement(sqlQuery))  {
            ps.setString(1,status);
            ps.setInt(2, receiptId);
            ps.executeUpdate();
        }
    }
}
