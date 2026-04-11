package com.example.project_oop.controller.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.ReaderService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.security.SecureRandom;
import java.sql.SQLException;

public class AddReaderController {
    private static final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AUTO_PASSWORD_LENGTH = 8;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtFullName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    @FXML
    private  Button btnCancel;

    @FXML
    private Button btnAddReader;


    @FXML
    public void initialize() {
        System.out.println("Giao diện Thêm Độc Giả Mới đã sẵn sàng!");

        txtPassword.setEditable(false);
        txtConfirmPassword.setEditable(false);

        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                txtPassword.clear();
                txtConfirmPassword.clear();
                return;
            }

            String generatedPassword = generateRandomPassword(AUTO_PASSWORD_LENGTH);
            txtPassword.setText(generatedPassword);
            txtConfirmPassword.setText(generatedPassword);
        });
    }


    private final ReaderService readerService = new ReaderService();

    @FXML
    void handleAddReader(ActionEvent event)  {
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        String userName = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (fullName.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Thiếu thông tin", "Vui lòng nhập đầy đủ họ tên, số điện thoại và username.");
            return;
        }



        Reader newReader = new Reader();
        newReader.setFullName(fullName);
        newReader.setPhone(phone);
        newReader.setUsername(userName);
        newReader.setPassword(password);

        try {
            readerService.registerNewReader(newReader);
            showAlert(AlertType.INFORMATION, "Tạo độc giả thành công",
                    "Tài khoản đã được tạo.\nMật khẩu tạm thời: " + password);
            Stage currentStage = (Stage) btnAddReader.getScene().getWindow();
            currentStage.close();
        } catch (SQLException e){
            showAlert(AlertType.ERROR, "Lỗi cơ sở dữ liệu", "Không thể tạo độc giả mới. Vui lòng thử lại.");
        }


    }

    @FXML
    void handleCancel(ActionEvent event) {
        System.out.println("Đóng cửa sổ Thêm Độc Giả...");
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }

    private String generateRandomPassword(int length) {
        if (length < 3) {
            throw new IllegalArgumentException("Password length must be at least 3");
        }

        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";

        StringBuilder password = new StringBuilder(length);
        password.append(upperChars.charAt(SECURE_RANDOM.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(SECURE_RANDOM.nextInt(lowerChars.length())));
        password.append(numberChars.charAt(SECURE_RANDOM.nextInt(numberChars.length())));

        for (int i = 3; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(PASSWORD_CHARACTERS.length());
            password.append(PASSWORD_CHARACTERS.charAt(index));
        }

        for (int i = password.length() - 1; i > 0; i--) {
            int j = SECURE_RANDOM.nextInt(i + 1);
            char current = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, current);
        }

        return password.toString();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

