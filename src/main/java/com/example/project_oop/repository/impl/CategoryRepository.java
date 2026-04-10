package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Category;
import com.example.project_oop.repository.ICategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{

    @Override
    public List<Category> getAll(){
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, category_name FROM categories ORDER BY category_name";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if(conn == null) return list;
            try(PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()){
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

    @Override
    public boolean add(String name, Connection conn) throws SQLException{
        if(name == null || name.isBlank()) return false;
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name.trim());
            ps.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean update(int id, String newName, Connection conn) throws SQLException{
        if(newName == null || newName.isBlank()) return false;
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, newName.trim());
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean delete(int id, Connection conn) throws SQLException{
        String checkSql = "SELECT COUNT(*) FROM books WHERE category_id = ?";
        try(PreparedStatement check = conn.prepareStatement(checkSql)){
            check.setInt(1, id);
            try(ResultSet rs = check.executeQuery()){
                if(rs.next() && rs.getInt(1) > 0){
                    return false; // Thể loại đang được sách dùng, không xóa
                }
            }
        }
        String deleteSql = "DELETE FROM categories WHERE category_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(deleteSql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean existsByName(String name){
        String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ?";
        try(Connection conn = DatabaseConnection.getConnection()){
            if(conn == null) return false;
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, name.trim());
                try(ResultSet rs = ps.executeQuery()){
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
