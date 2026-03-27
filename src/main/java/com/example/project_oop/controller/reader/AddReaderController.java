package com.example.project_oop.controller.reader;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class AddReaderController {
    @FXML private  Button btnCancel;
    @FXML private  Button btnRegister;


    @FXML
    public void initialize() {
        System.out.println("Giao diện Thêm Độc Giả Mới đã sẵn sàng!");
    }

    @FXML
    void handleCancel(ActionEvent event) {
        System.out.println("Đóng cửa sổ Thêm Độc Giả...");
        Stage currentStage = (Stage) btnCancel.getScene().getWindow();
        currentStage.close();
    }
}

