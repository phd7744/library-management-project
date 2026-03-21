package com.example.project_oop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class DashBoardController {
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

    }
}

