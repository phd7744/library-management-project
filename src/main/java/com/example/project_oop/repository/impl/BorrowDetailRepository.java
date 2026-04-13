package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.view.BorrowDetailView;
import com.example.project_oop.repository.IBorrowDetailRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDetailRepository implements IBorrowDetailRepository {

    @Override
    public void create(BorrowDetail detail, Connection conn) throws SQLException {
        String sqlQuery = """
                INSERT INTO borrow_details (receipt_id, book_id, due_date)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, detail.getReceiptId());
            ps.setInt(2, detail.getBookId());
            ps.setDate(3, detail.getDueDate());
            ps.executeUpdate();
        }
    }

    @Override
    public void returnBook(BorrowDetail bd, Connection conn) throws SQLException {
        String sqlQuery = """
                UPDATE borrow_details
                SET return_date = ?,
                    fine_amount = ?
                WHERE detail_id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setDate(1, bd.getReturnDate());
            ps.setDouble(2, bd.getFineAmount());
            ps.setInt(3, bd.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<BorrowDetail> getByReceiptId(int receiptId) throws SQLException {

        List<BorrowDetail> list = new ArrayList<>();

        String sqlQuery = """
                SELECT bd.detail_id, bd.receipt_id, bd.return_date,bd.book_id, b.title, bd.due_date, bd.fine_amount
                FROM borrow_details bd
                JOIN books b ON bd.book_id = b.book_id
                WHERE bd.receipt_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlQuery)) {

            ps.setInt(1, receiptId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowDetailView bdv = new BorrowDetailView();
                bdv.setId(rs.getInt("detail_id"));
                bdv.setReceiptId(rs.getInt("receipt_id"));
                bdv.setBookId(rs.getInt("book_id"));
                bdv.setDueDate(rs.getDate("due_date"));
                bdv.setReturnDate(rs.getDate("return_date"));
                bdv.setFineAmount(rs.getDouble("fine_amount"));
                bdv.setBookTitle(rs.getString("title"));

                list.add(bdv);
            }
        }

        return list;
    }

}