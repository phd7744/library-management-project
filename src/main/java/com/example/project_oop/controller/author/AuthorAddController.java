package com.example.project_oop.controller.author;

import com.example.project_oop.service.AuthorService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Optional;

public class AuthorAddController {

    private final AuthorService authorService;

    public AuthorAddController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public boolean show() {

        Dialog<String> dialog = buildDialog(
                "Add Author",
                "Nhập tên tác giả mới:",
                null
        );

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) return false;

        String name = result.get();

        if (authorService.existsByName(name)) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Trùng tên",
                    "Tác giả \"" + name + "\" đã tồn tại!"
            );
            return false;
        }

        try {

            authorService.addAuthor(name);

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Thành công",
                    "Đã thêm tác giả \"" + name + "\"!"
            );

            return true;

        } catch (SQLException | RuntimeException e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Thất bại",
                    "Không thể thêm tác giả. Vui lòng kiểm tra kết nối CSDL."
            );

            return false;
        }
    }

    static Dialog<String> buildDialog(String title, String header, String prefillValue) {

        Dialog<String> dialog = new Dialog<>();

        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType confirmType = new ButtonType("Lưu", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                confirmType,
                ButtonType.CANCEL
        );

        TextField tfName = new TextField();
        tfName.setPromptText("Nguyễn Nhật Ánh");
        tfName.setPrefWidth(250);

        if (prefillValue != null)
            tfName.setText(prefillValue);

        GridPane grid = new GridPane();

        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 20));

        grid.add(new Label("Tên tác giả (*):"), 0, 0);
        grid.add(tfName, 1, 0);

        dialog.getDialogPane().setContent(grid);

        javafx.scene.Node confirmBtn =
                dialog.getDialogPane().lookupButton(confirmType);

        confirmBtn.setDisable(prefillValue == null || prefillValue.isBlank());

        tfName.textProperty().addListener((obs, old, val) ->
                confirmBtn.setDisable(val == null || val.isBlank())
        );

        dialog.setResultConverter(
                pressed -> pressed == confirmType
                        ? tfName.getText().trim()
                        : null
        );

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
