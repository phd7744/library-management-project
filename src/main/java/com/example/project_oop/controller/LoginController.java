package com.example.project_oop.controller;

import com.example.project_oop.MainApp;
import com.example.project_oop.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private final LoginService loginService = new LoginService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!loginService.authenticate(username, password)) {
            messageLabel.setText("Sai ten dang nhap hoac mat khau.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 960));
            stage.setTitle("Quan Ly Thu Vien");
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (IOException e) {
            messageLabel.setText("Khong the mo man hinh chinh.");
        }
    }
}