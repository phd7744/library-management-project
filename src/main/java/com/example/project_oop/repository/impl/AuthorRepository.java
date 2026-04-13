package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Author;
import com.example.project_oop.repository.IAuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository implements IAuthorRepository {

    @Override
    public void getAll(Connection conn) throws SQLException {
    }

    @Override
    public void getById(int authorId, Connection conn) throws SQLException {
    }

    public List<Author> getAll() {

        List<Author> authorList = new ArrayList<>();

        String sql =
            """
                SELECT author_id, author_name
                FROM authors
                ORDER BY author_id
            """;

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return authorList;

            try (
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            ) {

                while (rs.next()) {

                    authorList.add(
                        new Author(
                            rs.getInt("author_id"),
                            rs.getString("author_name")
                        )
                    );

                }

            }

        }
        catch (SQLException e) {
            // Error fetching authors
        }

        return authorList;
    }

    @Override
    public void create(Author author, Connection conn) throws SQLException {

        String sql =
            """
                INSERT INTO authors (author_name)
                VALUES (?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, author.getAuthorName());

            ps.executeUpdate();

        }
    }

    @Override
    public void update(Author author, Connection conn) throws SQLException {

        String sql =
            """
                UPDATE authors
                SET author_name = ?
                WHERE author_id = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, author.getAuthorName());
            ps.setInt(2, author.getAuthorId());

            ps.executeUpdate();

        }
    }

    @Override
    public void delete(int authorId, Connection conn) throws SQLException {

        String sql = "DELETE FROM authors WHERE author_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, authorId);

            ps.executeUpdate();

        }

    }

    public int countBooksByAuthor(int authorId) {
        String sql = "SELECT COUNT(*) as count FROM books WHERE author_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return 0;

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, authorId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("count");
                    }
                }
            }
        } catch (SQLException e) {
            // Error counting books by author
        }

        return 0;
    }
}
