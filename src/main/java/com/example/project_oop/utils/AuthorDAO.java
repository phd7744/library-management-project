package com.example.project_oop.utils;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Author;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD API cho Tác giả (Author)
 * Xem danh sách tác giả (Read)
 * Xóa tác giả (Delete)
 * Thêm tác giả (Create)
 * Cập nhật tác giả (Update)
 */

public class AuthorDAO {

    /* Read */
    public static ObservableList<Author> getAllAuthors(){
        ObservableList<Author> list = FXCollections.observableArrayList();
        String sql = "SELECT author_id, author_name FROM authors ORDER BY author_name";

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return list;

            try(
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
            ){
                while(rs.next()){
                    list.add(new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name")

                    ));
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    /* Create */
    public static boolean addAuthor(String name){
        if(name == null || name.isBlank()) return false;

        String sql = "INSERT INTO authors (author_name) VALUES (?)";

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;

            try(PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, name.trim());
                pstmt.executeUpdate();
            }

            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /* Update */
    public static boolean updateAuthor(int id, String newName){
        if(newName == null || newName.isBlank()) return false;

        String sql = "UPDATE authors SET author_name = ? WHERE author_id = ?";

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;

            try(PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, newName.trim());
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }

            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /* Delete */
    public static boolean deleteAuthor(int id){

        // Kiểm tra có sách nào đang dùng tác giả này không
        String checkSql = "SELECT COUNT(*) FROM books WHERE author_id = ?";
        String deleteSql = "DELETE FROM authors WHERE author_id = ?";

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;

            try(PreparedStatement check = conn.prepareStatement(checkSql)){
                check.setInt(1, id);

                try(ResultSet rs = check.executeQuery()){
                    if(rs.next() && rs.getInt(1) > 0){
                        // Có sách đang dùng → không xóa
                        return false;
                    }
                }
            }

            try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)){
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /* Kiểm tra trùng tên */
    public static boolean existsByName(String name){

        String sql = "SELECT COUNT(*) FROM authors WHERE author_name = ?";

        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;

            try(PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, name.trim());

                try(ResultSet rs = pstmt.executeQuery()){
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}