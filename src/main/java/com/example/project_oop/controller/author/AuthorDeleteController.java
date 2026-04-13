package com.example.project_oop.controller.author;

import java.sql.SQLException;
import java.util.Optional;

import com.example.project_oop.models.Author;
import com.example.project_oop.service.AuthorService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AuthorDeleteController {

    private final AuthorService authorService;

    public AuthorDeleteController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public boolean show(Author author) {

        Alert confirm = new Alert(AlertType.CONFIRMATION);

        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText(null);

        confirm.setContentText(
            "Bạn có chắc muốn xóa tác giả \"" + author.getAuthorName() + "\" không?\n"
            + "Thao tác này không thể hoàn tác."
        );

        Optional<ButtonType> choice = confirm.showAndWait();

        if (choice.isEmpty() || choice.get() != ButtonType.OK)
            return false;

        try {

            authorService.deleteAuthor(author.getAuthorId());

            showAlert(
                AlertType.INFORMATION,
                "Đã xóa",
                "Tác giả \"" + author.getAuthorName() + "\" đã được xóa."
            );

            return true;

        }
        catch (IllegalStateException e) {

            showAlert(
                AlertType.WARNING,
                "Không thể xóa",
                "Tác giả \"" + author.getAuthorName() + "\" đang được dùng bởi một hoặc nhiều sách.\n"
                + "Vui lòng chuyển các sách sang tác giả khác trước khi xóa."
            );

            return false;

        }
        catch (SQLException | RuntimeException e) {

            showAlert(
                AlertType.ERROR,
                "Thất bại",
                "Không thể xóa tác giả. Vui lòng kiểm tra kết nối CSDL."
            );

            return false;

        }
    }

    private void showAlert(AlertType type, String title, String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
