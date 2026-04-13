package com.example.project_oop.controller;

import com.example.project_oop.models.Employee;
import com.example.project_oop.service.EmployeeService;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Optional;

public class EmployeeDeleteController {

    private final EmployeeService employeeService;

    public EmployeeDeleteController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean show(Employee employee) {

        Dialog<Boolean> dialog = buildConfirmDialog(employee);

        Optional<Boolean> result = dialog.showAndWait();

        if (result.isEmpty() || !result.get()) return false;

        try {

            employeeService.deleteEmployee(employee.getId());

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Thành công",
                    "Đã xóa nhân viên \"" + employee.getFullName() + "\" thành công!"
            );

            return true;

        } catch (SQLException e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Thất bại",
                    "Không thể xóa nhân viên. Vui lòng kiểm tra lại."
            );

            return false;
        }
    }

    private Dialog<Boolean> buildConfirmDialog(Employee employee) {

        Dialog<Boolean> dialog = new Dialog<>();

        dialog.setTitle("Xóa Nhân Viên");
        dialog.setHeaderText("Xác nhận xóa");

        ButtonType deleteType = new ButtonType("Xóa", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                deleteType,
                ButtonType.CANCEL
        );

        Label lblMessage = new Label(
                "Bạn có chắc chắn muốn xóa nhân viên:\n" +
                        "\"" + employee.getFullName() + "\" (ID: " + employee.getId() + ")?"
        );

        VBox content = new VBox(20);

        content.setStyle("-fx-padding: 20; -fx-alignment: center;");
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().add(lblMessage);

        dialog.getDialogPane().setContent(content);

        javafx.scene.Node deleteBtn = dialog.getDialogPane().lookupButton(deleteType);
        deleteBtn.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");

        dialog.setResultConverter(pressed -> pressed == deleteType);

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
