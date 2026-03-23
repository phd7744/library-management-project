package com.example.project_oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ReaderRecordsController {
    @FXML
    private TableView<?> readerTableView;

    @FXML
    public void initialize() {
        System.out.println("Giao diện Quản lý Độc giả đã sẵn sàng!");
    }
}
