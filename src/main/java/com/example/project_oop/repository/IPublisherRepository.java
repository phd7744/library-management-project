package com.example.project_oop.repository;

import com.example.project_oop.models.Publisher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IPublisherRepository {

    void create(Publisher publisher, Connection conn) throws SQLException;

    List<Publisher> getAll();

    void getById(int publisherId, Connection conn) throws SQLException;

    void update(Publisher publisher, Connection conn) throws SQLException;

    void delete(int publisherId, Connection conn) throws SQLException;

    int countBooksByPublisher(int publisherId);
}

