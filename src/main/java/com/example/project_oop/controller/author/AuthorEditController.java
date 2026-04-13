package com.example.project_oop.controller.author;

import com.example.project_oop.models.Author;
import com.example.project_oop.service.AuthorService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Optional;

public class AuthorEditController {

    private final AuthorService authorService;

    public AuthorEditController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public boolean show(Author author) {

        Dialog<String> dialog = buildDialog(
                "Chỉnh sửa tác giả",
                "Cập nhật tên tác giả:",
                author.getAuthorName()
        );

        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) return false;

        String newName = result.get();

        // Nếu giữ nguyên tên hoặc tên mới không trùng với ai khác
        if (!newName.equals(author.getAuthorName()) && authorService.existsByName(newName)) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Trùng tên",
                    "Tác giả \"" + newName + "\" đã tồn tại!"
            );

            return false;
        }

        try {

            author.setAuthorName(newName);

            authorService.updateAuthor(author);

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Thành công",
                    "Đã cập nhật tác giả thành \"" + newName + "\"!"
            );

            return true;

        } catch (SQLException | RuntimeException e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Thất bại",
                    "Không thể cập nhật tác giả. Vui lòng kiểm tra kết nối CSDL."
            );

            return false;
        }
    }

    private Dialog<String> buildDialog(String title, String header, String prefillValue) {

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

        confirmBtn.setDisable(tfName.getText().isBlank());

        tfName.textProperty().addListener(
                (obs, old, val) -> confirmBtn.setDisable(val == null || val.isBlank())
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
