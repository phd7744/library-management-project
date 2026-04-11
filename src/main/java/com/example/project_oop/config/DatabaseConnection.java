package com.example.project_oop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Cấu hình thông tin kết nối CSDL
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "librarydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Đường dẫn kết nối (Connection URL)
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
    private static boolean schemaEnsured = false;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Khai báo sử dụng Driver của MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thực hiện mở kết nối
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            ensureSchemaColumns(connection);

        } catch (ClassNotFoundException e) {
            System.err.println(" Lỗi: Không tìm thấy thư viện MySQL JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(
                    " Lỗi: Không thể kết nối đến Database '" + DB_NAME + "'. Vui lòng kiểm tra lại XAMPP/MySQL!");
            e.printStackTrace();
        }
        return connection;
    }

    private static synchronized void ensureSchemaColumns(Connection connection) {
        if (schemaEnsured || connection == null) {
            return;
        }

        String checkSql = """
                SELECT COUNT(*) AS column_count
                FROM information_schema.columns
                WHERE table_schema = ?
                  AND table_name = 'readers'
                  AND column_name = 'first_login'
                """;

        String alterReaderSql = "ALTER TABLE readers ADD COLUMN first_login TINYINT(1) NOT NULL DEFAULT 1 AFTER password";
        String alterEmployeeSql = "ALTER TABLE employees ADD COLUMN first_login TINYINT(1) NOT NULL DEFAULT 1 AFTER password";
        String hashLegacyPasswordsSql = """
                UPDATE readers
                SET password = LOWER(SHA2(password, 256))
                WHERE password IS NOT NULL
                  AND password <> ''
                  AND password NOT REGEXP '^[0-9a-fA-F]{64}$'
                """;
        String hashLegacyEmployeePasswordsSql = """
            UPDATE employees
            SET password = LOWER(SHA2(password, 256))
            WHERE password IS NOT NULL
              AND password <> ''
              AND password NOT REGEXP '^[0-9a-fA-F]{64}$'
            """;

        try (var ps = connection.prepareStatement(checkSql)) {
            ps.setString(1, DB_NAME);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt("column_count") == 0) {
                    try (Statement stmt = connection.createStatement()) {
                        stmt.executeUpdate(alterReaderSql);
                    }
                }
            }

            try (var employeePs = connection.prepareStatement("""
                    SELECT COUNT(*) AS column_count
                    FROM information_schema.columns
                    WHERE table_schema = ?
                      AND table_name = 'employees'
                      AND column_name = 'first_login'
                    """)) {
                employeePs.setString(1, DB_NAME);
                try (ResultSet rs = employeePs.executeQuery()) {
                    if (rs.next() && rs.getInt("column_count") == 0) {
                        try (Statement stmt = connection.createStatement()) {
                            stmt.executeUpdate(alterEmployeeSql);
                        }
                    }
                }
            }

            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(hashLegacyPasswordsSql);
                stmt.executeUpdate(hashLegacyEmployeePasswordsSql);
            }

            schemaEnsured = true;
        } catch (SQLException e) {
            System.err.println(" Lỗi: Không thể đảm bảo schema login columns!");
            e.printStackTrace();
        }
    }
}