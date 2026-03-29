package com.example.project_oop.utils;

import com.example.project_oop.config.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.project_oop.models.Book;

public class BookDAO {

    public static ObservableList<Book> getAllBooks(){
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String query =  "SELECT b.book_id, b.title, c.category_name, a.author_name, p.pub_name, b.quantity, b.status " +
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
                 ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("category_name"),
                        rs.getString("author_name"),
                        rs.getString("pub_name"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
                bookList.add(book);
            }
        }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    public static void deleteBook(int bookId){
        String query = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()){
            if (conn == null) return;
            try (PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();
            }
        } 
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void addBook(String title, int quantity, String status){
        String query = "INSERT INTO books (title, quantity, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()){
            if (conn == null) return;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, title);
                pstmt.setInt(2, quantity);
                pstmt.setString(3, status);
                pstmt.executeUpdate();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
