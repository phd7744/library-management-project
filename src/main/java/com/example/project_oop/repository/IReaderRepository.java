package com.example.project_oop.repository;

import com.example.project_oop.models.Reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IReaderRepository {
    //Chi khai bao , khong thuc thi chi tiet
    List<Reader> get();
    void add(Reader reader, Connection conn) throws SQLException;
    void update(Reader reader, Connection conn) throws SQLException;
    void delete(int id, Connection conn) throws SQLException;
}