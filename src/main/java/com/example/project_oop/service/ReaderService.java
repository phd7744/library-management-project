package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Reader;
import com.example.project_oop.repository.impl.ReaderRepository;
import com.example.project_oop.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;
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
        return PasswordUtil.hashPassword(rawPassword);
    }

}
