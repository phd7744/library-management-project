package com.example.project_oop.controller; // Bối nhớ sửa package cho đúng

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblMessage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    public void initialize() {
        System.out.println("Màn hình Đăng nhập đã sẵn sàng!");
    }

    // HÀM XỬ LÝ KHI BẤM NÚT LOGIN
    @FXML
    void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // 1. Kiểm tra bỏ trống
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Error: Please enter both username and password!");
            return;
        }

        // 2. Kiểm tra dữ liệu giả (Sau này sẽ thay bằng check Database)
        if (username.equals("admin") && password.equals("123")) {
            lblMessage.setText("Success: Login successful!");
            lblMessage.setStyle("-fx-text-fill: #10b981;"); // Màu xanh lá thành công

            // LƯU Ý CHO BỐI: Bài sau mình sẽ hướng dẫn code chuyển sang Main View ở đây nhé!
            System.out.println(">>> Chuyển sang màn hình chính...");
        } else {
            lblMessage.setText("Error: Invalid username or password!");
            lblMessage.setStyle("-fx-text-fill: #ef4444;"); // Trả lại màu đỏ lỗi
        }
    }
}