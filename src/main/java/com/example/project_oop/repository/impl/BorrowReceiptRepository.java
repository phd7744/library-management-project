package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.BorrowReceipt;
import com.example.project_oop.models.view.BorrowReceiptView;
import com.example.project_oop.repository.IBorrowReceiptRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowReceiptRepository implements IBorrowReceiptRepository {
    @Override
    public int create(BorrowReceipt borrowReceipt, Connection conn) throws SQLException {
        String sqlQuery = """
                INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status)
                VALUES (?,?,?,?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, borrowReceipt.getReaderId());
            ps.setInt(2, borrowReceipt.getEmpId());
            ps.setDate(3, (Date) borrowReceipt.getBorrowDate());
            ps.setString(4, borrowReceipt.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    @Override
    public void getById(int receiptId, Connection conn) throws SQLException {
        String sqlQuery = """
                SELECT * FROM borrow_receipts as br
                WHERE br.receipt_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, receiptId);
            ps.executeUpdate();
        }
    }

    @Override
    public void getActiveByReaderId(int reader_id, Connection conn) throws SQLException {
        String sqlQuery = """
                SELECT br.status FROM borrow_receipts as br
                WHERE br.reader_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, reader_id);
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
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, status);
            ps.setInt(2, receiptId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<BorrowReceipt> get() {

        List<BorrowReceipt> borrowReceiptList = new ArrayList<>();
        String sqlQuery = """
                SELECT br.receipt_id, br.reader_id, br.emp_id, br.borrow_date, br.status,
                       r.full_name, COUNT(bd.book_id) as total_books
                FROM borrow_receipts as br
                JOIN readers as r ON br.reader_id = r.reader_id
                LEFT JOIN borrow_details as bd ON br.receipt_id = bd.receipt_id
                GROUP BY br.receipt_id, br.reader_id, br.emp_id, br.borrow_date, br.status, r.full_name
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlQuery);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowReceiptView brv = new BorrowReceiptView();
                brv.setReceiptId(rs.getInt("receipt_id"));
                brv.setReaderId(rs.getInt("reader_id"));
                brv.setEmpId(rs.getInt("emp_id"));
                brv.setBorrowDate(rs.getDate("borrow_date"));
                brv.setStatus(rs.getString("status"));
                brv.setReaderName(rs.getString("full_name"));
                brv.setTotalBooks(rs.getInt("total_books"));

                borrowReceiptList.add(brv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowReceiptList;
    }
}
