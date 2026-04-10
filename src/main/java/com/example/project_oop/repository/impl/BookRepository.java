package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Book;
import com.example.project_oop.repository.IBookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements IBookRepository{

    @Override
    public List<Book> getAll(){
        List<Book> bookList = new ArrayList<>();
        String sql =
            """
                SELECT b.book_id, b.isbn, b.title,
                       c.category_name, a.author_name, p.pub_name,
                       b.publish_year, b.quantity, b.status
                FROM books b
                LEFT JOIN categories c ON b.category_id = c.category_id
                LEFT JOIN authors a    ON b.author_id = a.author_id
                LEFT JOIN publishers p ON b.pub_id = p.pub_id
                ORDER BY b.book_id
            """;

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return bookList;
            try(PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    bookList.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category_name"),
                        rs.getString("author_name"),
                        rs.getString("pub_name"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                    ));
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public boolean add(Book book, Connection conn) throws SQLException{
        int authorId = getOrCreate(
            conn,
            "SELECT author_id FROM authors WHERE author_name = ?",
            "INSERT INTO authors (author_name) VALUES (?)",
            book.getAuthor()
        );

        String pubName = (book.getPublisher() == null || book.getPublisher().isBlank()) ? "Chưa rõ" : book.getPublisher();
        int pubId = getOrCreate(
            conn,
            "SELECT pub_id FROM publishers WHERE pub_name = ?",
            "INSERT INTO publishers (pub_name) VALUES (?)",
            pubName
        );

        int categoryId = getOrCreate(
            conn,
            "SELECT category_id FROM categories WHERE category_name = ?",
            "INSERT INTO categories (category_name) VALUES (?)",
            book.getCategory()
        );

        if(authorId < 0 || pubId < 0 || categoryId < 0) return false;

        String insertSql =
            """
                INSERT INTO books (isbn, title, author_id, pub_id, category_id, publish_year, quantity, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')
            """;
        try(PreparedStatement ps = conn.prepareStatement(insertSql)){
            ps.setString(1, (book.getIsbn() == null || book.getIsbn().isBlank()) ? "" : book.getIsbn().trim());
            ps.setString(2, book.getTitle());
            ps.setInt(3, authorId);
            ps.setInt(4, pubId);
            ps.setInt(5, categoryId);
            if(book.getPublishYear() > 0) ps.setInt(6, book.getPublishYear());
            else ps.setNull(6, Types.INTEGER);
            ps.setInt(7, book.getQuantity());
            ps.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean update(Book book, Connection conn) throws SQLException{
        int authorId = getOrCreate(
            conn,
            "SELECT author_id FROM authors WHERE author_name = ?",
            "INSERT INTO authors (author_name) VALUES (?)",
            book.getAuthor()
        );

        String pubName = (book.getPublisher() == null || book.getPublisher().isBlank()) ? "Chưa rõ" : book.getPublisher();
        int pubId = getOrCreate(
            conn,
            "SELECT pub_id FROM publishers WHERE pub_name = ?",
            "INSERT INTO publishers (pub_name) VALUES (?)",
            pubName
        );

        int categoryId = getOrCreate(
            conn,
            "SELECT category_id FROM categories WHERE category_name = ?",
            "INSERT INTO categories (category_name) VALUES (?)",
            book.getCategory()
        );

        if(authorId < 0 || pubId < 0 || categoryId < 0) return false;

        String updateSql =
            """
                UPDATE books
                SET isbn=?, title=?, author_id=?, pub_id=?, category_id=?,
                    publish_year=?, quantity=?, status=?
                WHERE book_id=?
            """;
        try(PreparedStatement ps = conn.prepareStatement(updateSql)){
            ps.setString(1, (book.getIsbn() == null || book.getIsbn().isBlank()) ? "" : book.getIsbn().trim());
            ps.setString(2, book.getTitle());
            ps.setInt(3, authorId);
            ps.setInt(4, pubId);
            ps.setInt(5, categoryId);
            if (book.getPublishYear() > 0) ps.setInt(6, book.getPublishYear());
            else ps.setNull(6, Types.INTEGER);
            ps.setInt(7, book.getQuantity());
            ps.setString(8, (book.getStatus() == null || book.getStatus().isBlank()) ? "ACTIVE" : book.getStatus());
            ps.setInt(9, book.getId());
            ps.executeUpdate();
        }
        return true;
    }

    @Override
    public void delete(int bookId, Connection conn) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
        }
    }

    public int getOrCreate(Connection conn, String selectSql, String insertSql, String name) throws SQLException {
        if(name == null || name.isBlank()) return -1;
        try(PreparedStatement sel = conn.prepareStatement(selectSql)){
            sel.setString(1, name.trim());
            try(ResultSet rs = sel.executeQuery()){
                if(rs.next()) return rs.getInt(1);
            }
        }
        try(PreparedStatement ins = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            ins.setString(1, name.trim());
            ins.executeUpdate();
            try(ResultSet keys = ins.getGeneratedKeys()){
                if(keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
}
