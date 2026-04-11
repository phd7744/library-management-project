package com.example.project_oop.controller;

import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EmployeePasswordChangeController {
    private final EmployeeService employeeService = new EmployeeService();
    private Employee employee;
    private boolean passwordChanged;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label lblMessage;

    @FXML
    public void initialize() {
        passwordChanged = false;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (txtUsername != null && employee != null) {
            txtUsername.setText(employee.getUsername());
        }
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
        if (employee == null) {
            showMessage("Khong tim thay tai khoan can doi mat khau.");
            return;
        }

        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (newPassword == null || confirmPassword == null || newPassword.isBlank() || confirmPassword.isBlank()) {
            showMessage("Vui long nhap day du mat khau moi.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showMessage("Xac nhan mat khau khong khop.");
            return;
        }

        if (!isStrongPassword(newPassword)) {
            showMessage("Mat khau moi toi thieu 8 ky tu, gom chu hoa, chu thuong va so.");
            return;
        }

        try {
            employeeService.changeEmployeePassword(employee.getId(), newPassword);
            passwordChanged = true;
            closeWindow(event);
        } catch (SQLException e) {
            showMessage("Khong the doi mat khau. Vui long thu lai.");
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeWindow(event);
    }

    private boolean isStrongPassword(String password) {
        return password != null
                && password.matches("^[A-Za-z0-9]{8,}$")
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*");
    }

    private void showMessage(String message) {
        if (lblMessage != null) {
            lblMessage.setText(message);
        }
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
