package com.example.project_oop.repository;

import com.example.project_oop.models.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICategoryRepository{
    List<Category> getAll();
    boolean add(String name, Connection conn) throws SQLException;
    boolean update(int id, String newName, Connection conn) throws SQLException;
    boolean delete(int id, Connection conn) throws SQLException;
    boolean existsByName(String name);
}
