package com.example.project_oop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void showDashBoard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/dash-board.fxml"));
            Parent dashBoardView = loader.load();

            // Bước B: Dán giao diện mới vào giữa BorderPane, đè lên cái Dashboard cũ
            mainBorderPane.setCenter(dashBoardView);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }


    @FXML
    public void showBookInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/book-inventory-view.fxml"));
            Parent bookInventoryView = loader.load();

            // Bước B: Dán giao diện mới vào giữa BorderPane, đè lên cái Dashboard cũ
            mainBorderPane.setCenter(bookInventoryView);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }
}