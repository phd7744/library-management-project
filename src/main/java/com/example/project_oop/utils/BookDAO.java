package com.example.project_oop.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.project_oop.models.Book;

/** CRUD API
  * Xem danh sách của sách (Read)
  * Xóa sách (Delete)
  * Thêm sách (Create)
  * Cập nhật sách (Update)
  * Tìm kiếm sách (Read)
  */

public class BookDAO {

    /* Read */
    public static ObservableList<Book> getAllBooks() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String query = "SELECT b.book_id, b.isbn, b.title, c.category_name, a.author_name, p.pub_name, b.publish_year, b.quantity, b.status " +
                "FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.category_id " +
                "LEFT JOIN authors a ON b.author_id = a.author_id " +
                "LEFT JOIN publishers p ON b.pub_id = p.pub_id";

        try (Connection conn = DatabaseConnection.getConnection()){
            if (conn == null) {
                System.err.println("Không thể kết nối CSDL.");
                return bookList;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                    ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("category_name"),
                        rs.getString("author_name"),
                        rs.getString("pub_name"),
                        rs.getInt("publish_year"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                    );
                    bookList.add(book);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    /* Delete */
    public static void deleteBook(int bookId){
        String query = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /* Create */
    public static boolean addBook(String isbn, String title, String authorName, String publisherName, String categoryName, int publishYear, int quantity) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;

            // 1. Lấy hoặc tạo author
            int authorId = getOrCreate(conn,
                "SELECT author_id FROM authors WHERE author_name = ?",
                "INSERT INTO authors (author_name) VALUES (?)",
                authorName
            );

            // 2. Lấy hoặc tạo publisher (nếu để trống thì dùng "Chưa rõ")
            String pubName = (publisherName == null || publisherName.isBlank()) ? "Chưa rõ" : publisherName;
            int pubId = getOrCreate(conn,
                "SELECT pub_id FROM publishers WHERE pub_name = ?",
                "INSERT INTO publishers (pub_name) VALUES (?)",
                pubName
            );

            // 3. Lấy hoặc tạo category
            int categoryId = getOrCreate(conn,
                "SELECT category_id FROM categories WHERE category_name = ?",
                "INSERT INTO categories (category_name) VALUES (?)",
                categoryName
            );

            if (authorId < 0 || pubId < 0 || categoryId < 0) return false;

            String insertSql = "INSERT INTO books (isbn, title, author_id, pub_id, category_id, publish_year, quantity, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVE')";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, (isbn == null || isbn.isBlank()) ? "" : isbn.trim());
                pstmt.setString(2, title);
                pstmt.setInt(3, authorId);
                pstmt.setInt(4, pubId);
                pstmt.setInt(5, categoryId);
                if (publishYear > 0) {
                    pstmt.setInt(6, publishYear);
                } else {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                }
                pstmt.setInt(7, quantity);
                pstmt.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Update */
    public static boolean updateBook(int bookId, String isbn, String title, String authorName, String publisherName, String categoryName, int publishYear, int quantity) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;

            int authorId = getOrCreate(conn,
                "SELECT author_id FROM authors WHERE author_name = ?",
                "INSERT INTO authors (author_name) VALUES (?)",
                authorName
            );
            String pubName = (publisherName == null || publisherName.isBlank()) ? "Chưa rõ" : publisherName;
            int pubId = getOrCreate(conn,
                "SELECT pub_id FROM publishers WHERE pub_name = ?",
                "INSERT INTO publishers (pub_name) VALUES (?)",
                pubName
            );
            int categoryId = getOrCreate(conn,
                "SELECT category_id FROM categories WHERE category_name = ?",
                "INSERT INTO categories (category_name) VALUES (?)",
                categoryName
            );

            if (authorId < 0 || pubId < 0 || categoryId < 0) return false;

            String updateSql = "UPDATE books SET isbn=?, title=?, author_id=?, pub_id=?, category_id=?, publish_year=?, quantity=? "
                    + "WHERE book_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, (isbn == null || isbn.isBlank()) ? "" : isbn.trim());
                pstmt.setString(2, title);
                pstmt.setInt(3, authorId);
                pstmt.setInt(4, pubId);
                pstmt.setInt(5, categoryId);
                if (publishYear > 0) {
                    pstmt.setInt(6, publishYear);
                } else {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                }
                pstmt.setInt(7, quantity);
                pstmt.setInt(8, bookId);
                pstmt.executeUpdate();
            }
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tạo mới giá trị nếu chưa có
    private static int getOrCreate(Connection conn, String selectSql, String insertSql, String name) throws SQLException {
        if (name == null || name.isBlank()) return -1;
        try (PreparedStatement sel = conn.prepareStatement(selectSql)) {
            sel.setString(1, name.trim());
            try (ResultSet rs = sel.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        try (PreparedStatement ins = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ins.setString(1, name.trim());
            ins.executeUpdate();
            try (ResultSet keys = ins.getGeneratedKeys()) {
                if(keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
}
