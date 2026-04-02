package com.example.project_oop.controller;

import com.example.project_oop.MainApp;
import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginService;
import com.example.project_oop.service.LoginSession;
import com.example.project_oop.utils.AppLogger;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
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
    private ToggleButton adminToggle;

    @FXML
    private ToggleButton employeeToggle;

    @FXML
    public void initialize() {
        updateRoleUi(LoginRole.ADMIN);
    }

    @FXML
    public void handleRoleSelection(ActionEvent event) {
        updateRoleUi(getSelectedRole());
        AppLogger.logUserAction("Switch login role to " + getSelectedRole().getDisplayName());
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LoginRole selectedRole = getSelectedRole();
        String normalizedUsername = username == null ? "" : username.trim();

        LOGGER.log(Level.INFO, "Login attempt: role={0}, username={1}",
                new Object[]{selectedRole.getDisplayName(), normalizedUsername});

        if (!loginService.authenticate(selectedRole, username, password)) {
            LOGGER.log(Level.WARNING, "Login failed: role={0}, username={1}",
                    new Object[]{selectedRole.getDisplayName(), normalizedUsername});
            messageLabel.setText("Sai ten dang nhap hoac mat khau.");
            return;
        }

        LoginSession.setCurrentUser(normalizedUsername, selectedRole);
        LOGGER.log(Level.INFO, "Login success: role={0}, username={1}",
                new Object[]{selectedRole.getDisplayName(), normalizedUsername});
        AppLogger.logUserAction("Login success");

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/main-view.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root, 1280, 960);
            attachUserActionLogger(mainScene);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle(selectedRole == LoginRole.ADMIN ? "Quan Ly Thu Vien" : "Quan Ly Thu Vien - Employee");
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Open main screen failed after login", e);
            messageLabel.setText("Khong the mo man hinh chinh.");
        }
    }

    private LoginRole getSelectedRole() {
        return employeeToggle.isSelected() ? LoginRole.EMPLOYEE : LoginRole.ADMIN;
    }

    private void updateRoleUi(LoginRole role) {
        adminToggle.setSelected(role == LoginRole.ADMIN);
        employeeToggle.setSelected(role == LoginRole.EMPLOYEE);
        formTitleLabel.setText("Dang nhap " + role.getDisplayName());
        accountHintLabel.setText("Tai khoan mac dinh: " + role.getDefaultUsername() + " / " + role.getDefaultPassword());
        messageLabel.setText("");
    }

    private void attachUserActionLogger(Scene scene) {
        scene.addEventFilter(ActionEvent.ACTION, event -> {
            EventTarget target = event.getTarget();
            String actionName = "Unknown";

            if (target instanceof Labeled labeled) {
                actionName = labeled.getText();
            } else if (target instanceof Node node && node.getId() != null && !node.getId().isBlank()) {
                actionName = node.getId();
            } else if (target != null) {
                actionName = target.getClass().getSimpleName();
            }

            AppLogger.logUserAction("UI action: " + actionName);
        });
    }
}