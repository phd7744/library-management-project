package com.example.project_oop.controller.category;

import com.example.project_oop.service.CategoryService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class CategoryAddController {

    private final CategoryService categoryService;

    public CategoryAddController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public boolean show() {
        Dialog<String> dialog = buildDialog("Thêm Thể Loại", "Nhập tên thể loại mới:", null);
        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) return false;

        String name = result.get();
        if (categoryService.existsByName(name)) {
            showAlert(Alert.AlertType.WARNING, "Trùng tên", "Thể loại \"" + name + "\" đã tồn tại!");
            return false;
        }
        try {
            categoryService.addCategory(name);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm thể loại \"" + name + "\"!");
            return true;
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể thêm thể loại. Vui lòng kiểm tra kết nối CSDL.");
            return false;
        }
    }

    static Dialog<String> buildDialog(String title, String header, String prefillValue) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType confirmType = new ButtonType("Lưu", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmType, ButtonType.CANCEL);

        TextField tfName = new TextField();
        tfName.setPromptText("Khoa học máy tính");
        tfName.setPrefWidth(250);
        if (prefillValue != null) tfName.setText(prefillValue);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 20));
        grid.add(new Label("Tên thể loại (*):"), 0, 0);
        grid.add(tfName, 1, 0);
        dialog.getDialogPane().setContent(grid);

        javafx.scene.Node confirmBtn = dialog.getDialogPane().lookupButton(confirmType);
        confirmBtn.setDisable(prefillValue == null || prefillValue.isBlank());
        tfName.textProperty().addListener((obs, old, val) -> confirmBtn.setDisable(val == null || val.isBlank()));

        dialog.setResultConverter(pressed -> pressed == confirmType ? tfName.getText().trim() : null);
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
