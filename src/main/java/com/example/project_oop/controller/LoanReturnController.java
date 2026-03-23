package com.example.project_oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class LoanReturnController {

    @FXML
    private TableView<?> activeLoansTable;

    @FXML
    public void initialize() {
        System.out.println("Giao diện Mượn/Trả sách đã sẵn sàng!");
    }
}