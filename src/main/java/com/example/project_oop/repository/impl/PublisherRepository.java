package com.example.project_oop.repository.impl;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Publisher;
import com.example.project_oop.repository.IPublisherRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherRepository implements IPublisherRepository {

    @Override
    public void create(Publisher publisher, Connection conn) throws SQLException {
        String sql = "INSERT INTO publishers (pub_name, pub_address) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, publisher.getPublisherName());
            ps.setString(2, publisher.getPublisherAddress() != null ? publisher.getPublisherAddress() : "");
            ps.executeUpdate();
        }
    }

    @Override
    public List<Publisher> getAll() {
        List<Publisher> publishers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("ERROR: Database connection is null");
                return publishers;
            }
            String sql = "SELECT pub_id, pub_name, pub_address FROM publishers";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    publishers.add(new Publisher(
                        rs.getInt("pub_id"),
                        rs.getString("pub_name"),
                        rs.getString("pub_address")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR fetching publishers: " + e.getMessage());
        }
        return publishers;
    }

    @Override
    public void getById(int publisherId, Connection conn) throws SQLException {
    }

    @Override
    public void update(Publisher publisher, Connection conn) throws SQLException {
        String sql = "UPDATE publishers SET pub_name = ?, pub_address = ? WHERE pub_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, publisher.getPublisherName());
            ps.setString(2, publisher.getPublisherAddress() != null ? publisher.getPublisherAddress() : "");
            ps.setInt(3, publisher.getPublisherId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int publisherId, Connection conn) throws SQLException {
        String sql = "DELETE FROM publishers WHERE pub_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, publisherId);
            ps.executeUpdate();
        }
    }

    @Override
    public int countBooksByPublisher(int publisherId) {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM books WHERE pub_id = ?")) {
            ps.setInt(1, publisherId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR counting books by publisher: " + e.getMessage());
        }
        return count;
    }
}


