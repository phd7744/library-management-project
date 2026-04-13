package com.example.project_oop.controller;

import com.example.project_oop.MainApp;
import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;
import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnAuthorPage;

    @FXML
    private Button btnPublisherPage;

    @FXML
    public void initialize() {
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String fullName = LoginSession.getCurrentFullName();
        String accountUsername = LoginSession.getCurrentUsername();
        userNameLabel.setText((fullName != null && !fullName.isBlank()) ? fullName : resolveDisplayName(accountUsername, role));
        userRoleLabel.setText(role.getDisplayName());

        boolean isAdmin = role == LoginRole.ADMIN;
        if (btnEmployeeManagement != null) {
            btnEmployeeManagement.setVisible(isAdmin);
            btnEmployeeManagement.setManaged(isAdmin);
        }
    }

    private String resolveDisplayName(String username, LoginRole role) {
        if (username == null || username.isBlank()) {
            return role.getDisplayName();
        }

        try {
            Employee employee = new EmployeeService().findByUsernameAndRole(username, role);
            if (employee != null && employee.getFullName() != null && !employee.getFullName().isBlank()) {
                return employee.getFullName();
            }
        } catch (SQLException ignored) {
            return username;
        }

        return username;
    }

    @FXML
    public void showDashBoard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/dash-board.fxml"));
            Parent dashBoardView = loader.load();
            mainBorderPane.setCenter(dashBoardView);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Dashboard failed", e);
        }
    }


    @FXML
    public void showBookInventory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/book-inventory-view.fxml"));
            Parent bookInventoryView = loader.load();
            mainBorderPane.setCenter(bookInventoryView);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Book Inventory failed", e);
        }
    }

    @FXML
    public void showReaderRecords(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/reader-records-view.fxml"));
            Parent readerRecordsView = loader.load();
            mainBorderPane.setCenter(readerRecordsView);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Reader Records failed", e);
        }
    }

    @FXML
    public void showLoanPage(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/loan-return-view.fxml"));
            Parent loanPageView = loader.load();
            mainBorderPane.setCenter(loanPageView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Loan Return failed", e);
        }
    }

    @FXML
    public void showReportPage(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/report-view.fxml"));
            Parent reportPageView = loader.load();
            mainBorderPane.setCenter(reportPageView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Report failed", e);
        }
    }

    @FXML
    public void showCategoryPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/category-view.fxml"));
            Parent categoryView = loader.load();
            mainBorderPane.setCenter(categoryView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Category failed", e);
        }
    }

    @FXML
    public void showEmployeeManagement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/employee-management-view.fxml"));
            Parent employeeManagementView = loader.load();
            mainBorderPane.setCenter(employeeManagementView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Employee Management failed", e);
        }
    }

    @FXML
    public void showAuthorPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/author-view.fxml"));
            Parent authorView = loader.load();
            mainBorderPane.setCenter(authorView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Author failed", e);
        }
    }

    @FXML
    public void showPublisherPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/publisher-view.fxml"));
            Parent publisherView = loader.load();
            mainBorderPane.setCenter(publisherView);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Load Publisher failed", e);
        }
    }

    @FXML
    public void handleChangePassword(ActionEvent event) {
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String username = LoginSession.getCurrentUsername();
        if (username == null || username.isBlank()) {
            return;
        }

        try {
            Employee employee = new EmployeeService().findByUsernameAndRole(username, role);
            if (employee == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/employee-password-change-view.fxml"));
            Parent root = loader.load();

            EmployeePasswordChangeController controller = loader.getController();
            controller.setEmployee(employee);

            Stage dialog = new Stage();
            dialog.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
            dialog.setTitle("Doi mat khau");
            dialog.setScene(new Scene(root));
            dialog.setResizable(true);
            dialog.showAndWait();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Open change-password failed", e);
        }
    }

    @FXML
    public void handleLogout(ActionEvent event){
        try{
            LoginRole currentRole = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
            String currentUsername = LoginSession.getCurrentUsername() != null
                ? LoginSession.getCurrentUsername()
                : currentRole.getDisplayName();

            LOGGER.log(Level.INFO, "Logout: role={0}, username={1}",
                new Object[]{currentRole.getDisplayName(), currentUsername});

            LoginSession.clear();

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/project_oop/fxml/login-view.fxml"));
            Parent loginView = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginView));
            stage.setTitle("Quan Ly Thu Vien");
            stage.setResizable(true);
            stage.setMaximized(false);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Logout navigation failed", e);
        }
    }
}