package com.example.project_oop.controller;

import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;
import com.example.project_oop.utils.UsernameUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementController {
    private final EmployeeService employeeService = new EmployeeService();

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtTempPassword;

    @FXML
    private TextField tfSearch;

    @FXML
    private Label lblMessage;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, Integer> colEmpId;

    @FXML
    private TableColumn<Employee, String> colFullName;

    @FXML
    private TableColumn<Employee, String> colPhone;

    @FXML
    private TableColumn<Employee, String> colRole;

    @FXML
    private TableColumn<Employee, String> colUsername;

    @FXML
    private TableColumn<Employee, String> colStatus;

    @FXML
    private TableColumn<Employee, Boolean> colFirstLogin;

    @FXML
    private TableColumn<Employee, Void> colActions;

    private final EmployeeEditController editController = new EmployeeEditController(employeeService);
    private final EmployeeDeleteController deleteController = new EmployeeDeleteController(employeeService);

    @FXML
    public void initialize() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFirstLogin.setCellValueFactory(new PropertyValueFactory<>("firstLogin"));

        txtUsername.setEditable(false);
        txtTempPassword.setEditable(false);

        txtFullName.textProperty().addListener((observable, oldValue, newValue) -> {
            String generated = UsernameUtil.generateEmployeeUsername(newValue);
            txtUsername.setText(generated.isBlank() ? "employee" : generated);
        });

        // Setup search functionality
        if (tfSearch != null) {
            tfSearch.textProperty().addListener((obs, oldVal, newVal) -> searchEmployees(newVal));
        }

        // Setup action column
        setupActionColumn();

        reloadEmployees();
    }

    @FXML
    public void handleCreateEmployee(ActionEvent event) {
        String fullName = txtFullName.getText() == null ? "" : txtFullName.getText().trim();
        String phone = txtPhone.getText() == null ? "" : txtPhone.getText().trim();

        if (fullName.isEmpty()) {
            showMessage("Vui long nhap ho ten nhan vien.");
            return;
        }

        Employee employee = new Employee();
        employee.setFullName(fullName);
        employee.setPhone(phone);

        try {
            Employee savedEmployee = employeeService.registerNewEmployee(employee);
            txtFullName.clear();
            txtPhone.clear();
            txtUsername.clear();
            txtTempPassword.clear();
            reloadEmployees();
            txtUsername.setText(savedEmployee.getUsername());
            txtTempPassword.setText(savedEmployee.getPassword());
            showAlert("Tao nhan vien thanh cong",
                    "Username: " + savedEmployee.getUsername() + "\nMat khau tam thoi: " + savedEmployee.getPassword());
        } catch (SQLException e) {
            showMessage("Khong the tao nhan vien moi. Vui long thu lai.");
        }
    }

    private void setupActionColumn() {
        if (colActions == null) {
            System.err.println("ERROR: colActions is null - FXML binding issue");
            return;
        }

        colActions.setCellFactory(param -> new TableCell<Employee, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(6, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    if (editController.show(employee)) {
                        reloadEmployees();
                    }
                });

                deleteBtn.setOnAction(event -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    if (deleteController.show(employee)) {
                        reloadEmployees();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void searchEmployees(String searchTerm) {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        List<Employee> filtered = allEmployees.stream()
                .filter(emp -> emp.getFullName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        emp.getUsername().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();

        employeeTableView.setItems(FXCollections.observableArrayList(filtered));
    }

    private void reloadEmployees() {
        tfSearch.clear();
        employeeTableView.setItems(FXCollections.observableArrayList(employeeService.getAllEmployees()));
    }

    private void showMessage(String message) {
        if (lblMessage != null) {
            lblMessage.setText(message);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
