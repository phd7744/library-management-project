package com.example.project_oop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Cấu hình thông tin kết nối CSDL
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "librarydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Đường dẫn kết nối (Connection URL)
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;


    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Khai báo sử dụng Driver của MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thực hiện mở kết nối
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.err.println(" Lỗi: Không tìm thấy thư viện MySQL JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Lỗi: Không thể kết nối đến Database '" + DB_NAME + "'. Vui lòng kiểm tra lại XAMPP/MySQL!");
            e.printStackTrace();
        }
        return connection;
    }

}