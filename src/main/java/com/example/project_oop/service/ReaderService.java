package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Reader;
import com.example.project_oop.repository.impl.ReaderRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ReaderService {
    private final ReaderRepository readerRepository = new ReaderRepository();


    public List<Reader> getAllReaders() {
        return readerRepository.get();
    }


    public void registerNewReader(Reader reader) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            reader.setDebt(0);
            reader.setStatus("ACTIVE");
            reader.setFirstLogin(true);
            reader.setPassword(hashPassword(reader.getPassword()));
            readerRepository.add(reader, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    public void deleteReader(int readerId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            readerRepository.delete(readerId, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    public void updateReader(Reader reader) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            readerRepository.update(reader, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public Reader findByUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            return readerRepository.findByUsername(username.trim(), conn);
        }
    }

    public void changeReaderPassword(int readerId, String newPassword) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            readerRepository.updatePassword(readerId, hashPassword(newPassword), conn);
            readerRepository.markFirstLoginCompleted(readerId, conn);
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public String hashPassword(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hashedBytes.length * 2);
            for (byte hashedByte : hashedBytes) {
                hex.append(String.format("%02x", hashedByte));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

}
