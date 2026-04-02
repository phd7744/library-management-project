package com.example.project_oop.controller;

import com.example.project_oop.MainApp;
import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    public void initialize() {
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String username = LoginSession.getCurrentUsername() != null ? LoginSession.getCurrentUsername() : role.getDefaultUsername();

        userNameLabel.setText(username);
        userRoleLabel.setText(role.getDisplayName());
    }

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
    public void showCategoryPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/category-view.fxml"));
            Parent categoryView = loader.load();
            mainBorderPane.setCenter(categoryView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }

    @FXML
    public void handleLogout(ActionEvent event){
        try{
            LoginSession.clear();

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/login-view.fxml"));
            Parent loginView = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView, 1280, 960));
            stage.setTitle("Quan Ly Thu Vien");
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi tải trang: Kiểm tra lại đường dẫn file FXML!");
        }
    }
}