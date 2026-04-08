package com.example.project_oop.controller;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.LoginService;
import com.example.project_oop.service.ReaderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class CustomerLoginController {

    private final LoginService loginService = new LoginService();
    private final ReaderService readerService = new ReaderService();

    private Reader pendingPasswordChangeReader;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox passwordChangeBox;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void initialize() {
        passwordChangeBox.setManaged(false);
        passwordChangeBox.setVisible(false);
    }

    @FXML
    public void handleCustomerLogin(ActionEvent event) {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText();

        LoginService.CustomerLoginResult result = loginService.authenticateCustomer(username, password);
        switch (result.getStatus()) {
            case SUCCESS -> {
                messageLabel.setStyle("-fx-text-fill: #166534;");
                messageLabel.setText("Dang nhap test thanh cong.");
                hidePasswordChangeBox();
            }
            case REQUIRE_PASSWORD_CHANGE -> {
                pendingPasswordChangeReader = result.getReader();
                messageLabel.setStyle("-fx-text-fill: #92400e;");
                messageLabel.setText("Tai khoan dang dang nhap lan dau. Bat buoc doi mat khau.");
                showPasswordChangeBox();
            }
            case BANNED -> {
                messageLabel.setStyle("-fx-text-fill: #b91c1c;");
                messageLabel.setText("Tai khoan dang bi baned/banned. Vui long lien he thu vien.");
                hidePasswordChangeBox();
            }
            case INVALID_CREDENTIALS -> {
                messageLabel.setStyle("-fx-text-fill: #b91c1c;");
                messageLabel.setText("Sai mat khau, hay nho nhan vien cap mat khau moi.");
                hidePasswordChangeBox();
            }
            default -> {
                messageLabel.setStyle("-fx-text-fill: #b91c1c;");
                messageLabel.setText("Co loi he thong. Vui long thu lai.");
                hidePasswordChangeBox();
            }
        }
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
        if (pendingPasswordChangeReader == null) {
            messageLabel.setStyle("-fx-text-fill: #b91c1c;");
            messageLabel.setText("Khong tim thay tai khoan can doi mat khau.");
            return;
        }

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword == null || confirmPassword == null || newPassword.isBlank() || confirmPassword.isBlank()) {
            messageLabel.setStyle("-fx-text-fill: #b91c1c;");
            messageLabel.setText("Vui long nhap day du mat khau moi.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            messageLabel.setStyle("-fx-text-fill: #b91c1c;");
            messageLabel.setText("Xac nhan mat khau khong khop.");
            return;
        }

        if (!isStrongPassword(newPassword)) {
            messageLabel.setStyle("-fx-text-fill: #b91c1c;");
            messageLabel.setText("Mat khau moi toi thieu 8 ky tu, gom chu hoa, chu thuong va so.");
            return;
        }

        try {
            readerService.changeReaderPassword(pendingPasswordChangeReader.getId(), newPassword);
            messageLabel.setStyle("-fx-text-fill: #166534;");
            messageLabel.setText("Doi mat khau thanh cong. Ban co the dang nhap lai de test.");
            pendingPasswordChangeReader = null;
            newPasswordField.clear();
            confirmPasswordField.clear();
            hidePasswordChangeBox();
        } catch (SQLException e) {
            messageLabel.setStyle("-fx-text-fill: #b91c1c;");
            messageLabel.setText("Khong the doi mat khau. Vui long thu lai.");
        }
    }

    private void showPasswordChangeBox() {
        passwordChangeBox.setManaged(true);
        passwordChangeBox.setVisible(true);
    }

    private void hidePasswordChangeBox() {
        passwordChangeBox.setManaged(false);
        passwordChangeBox.setVisible(false);
        pendingPasswordChangeReader = null;
    }

    private boolean isStrongPassword(String password) {
        return password != null
                && password.matches("^[A-Za-z0-9]{8,}$")
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*");
    }
}
