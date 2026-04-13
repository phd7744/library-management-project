package com.example.project_oop.repository;

import com.example.project_oop.models.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IBookRepository {
    List<Book> getAll();

    boolean add(Book book, Connection conn) throws SQLException;

    boolean update(Book book, Connection conn) throws SQLException;

    void delete(int bookId, Connection conn) throws SQLException;

    Book getById(int bookId, Connection conn) throws SQLException;
}
