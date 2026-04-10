package com.example.project_oop.controller;

import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class DashBoardController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<?, ?> bookTitleColumn;

    @FXML
    private Button btnBookInventory;

    @FXML
    private Button btnDashBoard;

    @FXML
    private TableColumn<?, ?> dueDateColumn;

    @FXML
    private TableColumn<?, ?> loanDateColumn;

    @FXML
    private TableColumn<?, ?> loanIdColumn;

    @FXML
    private TableView<?> loanTableView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableColumn<?, ?> readerNameColumn;

    @FXML
    public void initialize() {
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String username = LoginSession.getCurrentUsername() != null ? LoginSession.getCurrentUsername() : role.getDisplayName();
        welcomeLabel.setText("Welcome back, " + username + "!");
    }
}

