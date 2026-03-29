package com.example.project_oop.repository.impl;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.Reader;
import com.example.project_oop.repository.IReaderRepository;
import com.example.project_oop.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class ReaderRepository implements IReaderRepository {
    @Override
    public List<Reader> get() {
        List<Reader> readerList = new ArrayList<>();
        String sqlQuery = """
                    SELECT r.*
                    FROM readers as r
                """;
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlQuery);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reader r = new Reader(
                        rs.getInt("reader_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getDouble("debt"),
                        rs.getString("status"),
                        rs.getString("username"),
                        rs.getString("password")
                        );
                readerList.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readerList;
    }

    @Override
    public void add(Reader reader, Connection conn) throws SQLException {
        String sqlQuery = """
                            INSERT INTO readers (full_name, phone_number, debt, username, password, status)
                            VALUES (?,?,?,?,?,?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, reader.getFullName());
            ps.setString(2, reader.getPhone());
            ps.setDouble(3, reader.getDebt());
            ps.setString(4, reader.getUsername());
            ps.setString(5, reader.getPassword());
            ps.setString(6, reader.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Reader reader, Connection conn) throws SQLException {
        String sqlQuery = """
                UPDATE readers
                SET full_name = ?,
                    phone_number = ?,
                    debt = ?,
                    status = ?
                WHERE reader_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, reader.getFullName());
            ps.setString(2, reader.getPhone());
            ps.setDouble(3, reader.getDebt());
            ps.setString(4, reader.getStatus());
            ps.setInt(5, reader.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id, Connection conn) throws SQLException {
        String sqlQuery = """
                DELETE FROM readers
                WHERE reader_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public String getSQL(BorrowDetail detail)  {
        String sqlQuery = """
                INSERT INTO borrow_details (receipt_id, book_id, due_date)
                VALUES (?,?,?)
                """;
        return sqlQuery;
    }
}
