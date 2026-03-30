package com.example.project_oop.utils;

import com.example.project_oop.models.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD API cho Thể loại (Category)
 * Xem danh sách của thể loại (Read)
 * Xóa thể loại (Delete)
 * Thêm thể loại (Create)
 * Cập nhật thể loại (Update)
 */
public class CategoryDAO {

    /* Read */
    public static ObservableList<Category> getAllCategories(){
        ObservableList<Category> list = FXCollections.observableArrayList();
        String sql = "SELECT category_id, category_name FROM categories ORDER BY category_name";
        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return list;
            try(
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    list.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /* Create */
    public static boolean addCategory(String name){
        if(name == null || name.isBlank()) return false;
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
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
    public static boolean updateCategory(int id, String newName){
        if(newName == null || newName.isBlank()) return false;
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
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
    public static boolean deleteCategory(int id){
        // Kiểm tra sách nào đang dùng thể loại
        String checkSql = "SELECT COUNT(*) FROM books WHERE category_id = ?";
        String deleteSql = "DELETE FROM categories WHERE category_id = ?";
        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;
            try(PreparedStatement check = conn.prepareStatement(checkSql)){
                check.setInt(1, id);
                try(ResultSet rs = check.executeQuery()){
                    if(rs.next() && rs.getInt(1) > 0){
                        // Có sách đang dùng — không thể xóa
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

    /* Kiểm tra trùng lặp */
    public static boolean existsByName(String name){
        String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ?";
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
