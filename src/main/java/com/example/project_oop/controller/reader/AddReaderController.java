package com.example.project_oop.controller.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.ReaderService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.sql.SQLException;

public class AddReaderController {
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
    }


    private final ReaderService readerService = new ReaderService();

    @FXML
    void handleAddReader(ActionEvent event)  {
        String fullName = txtFullName.getText();
        String phone = txtPhone.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (fullName.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            System.out.println("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Mật khẩu không khớp!");
            return;
        }



        Reader newReader = new Reader();
        newReader.setFullName(fullName);
        newReader.setPhone(phone);
        newReader.setUsername(userName);
        newReader.setPassword(password);

        try {
            readerService.registerNewReader(newReader);
            Stage currentStage = (Stage) btnAddReader.getScene().getWindow();
            currentStage.close();
        } catch (SQLException e){
            throw new RuntimeException("Lỗi DB", e);
        }


    }

    @FXML
    void handleCancel(ActionEvent event) {
        System.out.println("Đóng cửa sổ Thêm Độc Giả...");
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }
}

