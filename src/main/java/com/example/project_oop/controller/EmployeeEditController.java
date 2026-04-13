package com.example.project_oop.controller;

import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Optional;

public class EmployeeEditController {

    private final EmployeeService employeeService;

    public EmployeeEditController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean show(Employee employee) {

        Dialog<Employee> dialog = buildDialog(employee);

        Optional<Employee> result = dialog.showAndWait();

        if (result.isEmpty()) return false;

        Employee updated = result.get();

        try {

            employeeService.updateEmployee(updated);

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Thành công",
                    "Đã cập nhật thông tin nhân viên \"" + updated.getFullName() + "\"!"
            );

            return true;

        } catch (SQLException e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Thất bại",
                    "Không thể cập nhật nhân viên. Vui lòng thử lại."
            );

            return false;
        }
    }

    private Dialog<Employee> buildDialog(Employee employee) {

        Dialog<Employee> dialog = new Dialog<>();

        dialog.setTitle("Chỉnh Sửa Nhân Viên");
        dialog.setHeaderText("Cập nhật thông tin nhân viên");

        ButtonType saveType = new ButtonType("Lưu", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                saveType,
                ButtonType.CANCEL
        );

        TextField tfFullName = new TextField(employee.getFullName());
        tfFullName.setPromptText("Họ và tên");
        tfFullName.setPrefWidth(250);

        TextField tfPhone = new TextField(employee.getPhone() != null ? employee.getPhone() : "");
        tfPhone.setPromptText("Số điện thoại");
        tfPhone.setPrefWidth(250);

        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("LIBRARIAN", "ADMIN");
        cbRole.setValue(employee.getRole() != null ? employee.getRole() : "LIBRARIAN");
        cbRole.setPrefWidth(250);

        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("ACTIVE", "INACTIVE", "BANNED");
        cbStatus.setValue(employee.getStatus() != null ? employee.getStatus() : "ACTIVE");
        cbStatus.setPrefWidth(250);

        GridPane grid = new GridPane();

        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 20));

        grid.add(new Label("Họ Tên (*):"), 0, 0);
        grid.add(tfFullName, 1, 0);
        grid.add(new Label("Số Điện Thoại:"), 0, 1);
        grid.add(tfPhone, 1, 1);
        grid.add(new Label("Vai Trò:"), 0, 2);
        grid.add(cbRole, 1, 2);
        grid.add(new Label("Trạng Thái:"), 0, 3);
        grid.add(cbStatus, 1, 3);

        dialog.getDialogPane().setContent(grid);

        javafx.scene.Node saveBtn = dialog.getDialogPane().lookupButton(saveType);
        saveBtn.setDisable(tfFullName.getText().isBlank());

        tfFullName.textProperty().addListener(
                (obs, old, val) -> saveBtn.setDisable(val == null || val.isBlank())
        );

        dialog.setResultConverter(pressed -> {
            if (pressed == saveType) {
                employee.setFullName(tfFullName.getText().trim());
                employee.setPhone(tfPhone.getText().trim());
                employee.setRole(cbRole.getValue());
                employee.setStatus(cbStatus.getValue());
                return employee;
            }
            return null;
        });

        return dialog;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
