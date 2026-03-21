package com.example.project_oop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class BookInventoryController {

    // Khai báo trước Bảng dữ liệu để bài sau chúng ta dùng
    @FXML
    private TableView<?> bookTableView;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    public void initialize() {
        System.out.println("Giao diện Quản lý Sách đã sẵn sàng!");
    }
}
