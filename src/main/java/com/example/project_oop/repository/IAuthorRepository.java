package com.example.project_oop.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.project_oop.models.Author;

public interface IAuthorRepository {
    void create(Author author, Connection conn) throws SQLException;

    void getAll(Connection conn) throws SQLException;

    void getById(int authorId, Connection conn) throws SQLException;

    void update(Author author, Connection conn) throws SQLException;

    void delete(int authorId, Connection conn) throws SQLException;

}