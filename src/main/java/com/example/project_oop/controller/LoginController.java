package com.example.project_oop.controller;

import com.example.project_oop.MainApp;
import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;
import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginService;
import com.example.project_oop.service.LoginSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    private final LoginService loginService = new LoginService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Label formTitleLabel;

    @FXML
    private Label accountHintLabel;

    @FXML
    public void initialize() {
        formTitleLabel.setText("Dang nhap");
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String normalizedUsername = username == null ? "" : username.trim();

        LOGGER.log(Level.INFO, "Login attempt: username={0}", normalizedUsername);

        EmployeeService.EmployeeLoginResult result = loginService.authenticateAccount(username, password);

        if (result.getStatus() == EmployeeService.EmployeeLoginStatus.INVALID_CREDENTIALS) {
            LOGGER.log(Level.WARNING, "Login failed: username={0}", normalizedUsername);
            messageLabel.setText("Sai ten dang nhap hoac mat khau.");
            return;
        }

        if (result.getStatus() == EmployeeService.EmployeeLoginStatus.BANNED) {
            messageLabel.setText("Tai khoan dang bi baned/banned. Vui long lien he quan tri vien.");
            return;
        }

        if (result.getStatus() == EmployeeService.EmployeeLoginStatus.ERROR) {
            messageLabel.setText("Khong the dang nhap luc nay. Vui long thu lai.");
            return;
        }

        Employee employee = result.getEmployee();
        if (result.getStatus() == EmployeeService.EmployeeLoginStatus.REQUIRE_PASSWORD_CHANGE && employee != null) {
            boolean changed = showEmployeePasswordChangeDialog(employee);
            if (!changed) {
                return;
            }
        }

        LoginRole resolvedRole = resolveRole(result.getEmployee());
        LoginSession.setCurrentUser(normalizedUsername, resolvedRole);
        LOGGER.log(Level.INFO, "Login success: role={0}, username={1}",
                new Object[]{resolvedRole.getDisplayName(), normalizedUsername});

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/main-view.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Quan Ly Thu Vien");
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (IOException e) {
            messageLabel.setText("Khong the mo man hinh chinh.");
        }
    }

    private boolean showEmployeePasswordChangeDialog(Employee employeeAccount) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/employee-password-change-view.fxml"));
            Parent root = loader.load();

            EmployeePasswordChangeController controller = loader.getController();
            controller.setEmployee(employeeAccount);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Doi mat khau");
            dialog.setScene(new Scene(root));
            dialog.setResizable(true);
            dialog.showAndWait();

            return controller.isPasswordChanged();
        } catch (IOException e) {
            messageLabel.setText("Khong the mo man hinh doi mat khau.");
            return false;
        }
    }

    private LoginRole resolveRole(Employee employee) {
        if (employee == null || employee.getRole() == null) {
            return LoginRole.ADMIN;
        }

        String role = employee.getRole().trim().toUpperCase();
        return "ADMIN".equals(role) ? LoginRole.ADMIN : LoginRole.EMPLOYEE;
    }

    @FXML
    public void handleOpenCustomerLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/customer-login-view.fxml"));
            Parent root = loader.load();

            Stage customerStage = new Stage();
            customerStage.initModality(Modality.APPLICATION_MODAL);
            customerStage.setTitle("Customer Login Test");
            customerStage.setScene(new Scene(root));
            customerStage.setResizable(true);
            customerStage.showAndWait();
        } catch (IOException e) {
            messageLabel.setText("Khong the mo man hinh login customer.");
        }
    }

}