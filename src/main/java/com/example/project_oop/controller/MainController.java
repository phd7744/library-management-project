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
            mainBorderPane.setCenter(bookInventoryView);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }

    @FXML
    public void showReaderRecords(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/reader-records-view.fxml"));
            Parent readerRecordsView = loader.load();
            mainBorderPane.setCenter(readerRecordsView);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }

    @FXML
    public void showLoanPage(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/loan-return-view.fxml"));
            Parent loanPageView = loader.load();
            mainBorderPane.setCenter(loanPageView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }

    @FXML
    public void showReportPage(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/report-view.fxml"));
            Parent reportPageView = loader.load();
            mainBorderPane.setCenter(reportPageView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }

    @FXML
    public void handleLogout(ActionEvent event){
        try{
            System.out.println("Log out page");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }
}