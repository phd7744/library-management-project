package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.Reader;
import com.example.project_oop.models.view.BorrowDetailView;
import com.example.project_oop.repository.IBorrowDetailRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDetailRepository implements IBorrowDetailRepository {

    @Override
    public void add(BorrowDetail detail, Connection conn) throws SQLException {
        String sqlQuery =
            """
                INSERT INTO borrow_details (receipt_id, book_id, due_date)
                VALUES (?,?,?)
            """;
        try(PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1,detail.getReceiptId());
            ps.setInt(2,detail.getBookId());
            ps.setDate(3, (Date) detail.getDueDate());
            ps.executeUpdate();
        }
    }

    @Override
    public void returnBook(int detailId, Date returnDate, double fineAmount, Connection conn) throws SQLException {
        String sqlQuery =
            """
                UPDATE borrow_details
                SET return_date = ?,
                    fine_amount = ?
                WHERE detail_id =?
            """;
        try(PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setDate(1, returnDate);
            ps.setDouble(2, fineAmount);
            ps.setInt(3, detailId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<BorrowDetailView> getByReceiptId(int receiptId) throws SQLException {

        List<BorrowDetailView> list = new ArrayList<>();

        String sqlQuery =
            """
                SELECT bd.id, bd.receipt_id, bd.book_id, b.title, bd.due_date, bd.fine_amount
                FROM borrow_details bd
                JOIN books b ON bd.book_id = b.book_id
                WHERE bd.receipt_id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {

            ps.setInt(1, receiptId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowDetailView bd = new BorrowDetailView(
                    rs.getInt("id"),
                    rs.getInt("receipt_id"),
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getDate("due_date"),
                    rs.getDouble("fine_amount")
                );

                list.add(bd);
            }
        }

        return list;
    }


}
