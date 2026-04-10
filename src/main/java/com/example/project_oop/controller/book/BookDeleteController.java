package com.example.project_oop.controller.book;

import com.example.project_oop.models.Book;
import com.example.project_oop.service.BookService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class BookDeleteController{

    private final BookService bookService;

    public BookDeleteController(BookService bookService){
        this.bookService = bookService;
    }

    public boolean show(Book book){
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa sách \"" + book.getTitle() + "\" không?\n" + "Thao tác này không thể hoàn tác.");
        Optional<ButtonType> choice = confirm.showAndWait();
        if (choice.isEmpty() || choice.get() != ButtonType.OK) return false;

        try {
            bookService.deleteBook(book.getId());
            showAlert(AlertType.INFORMATION, "Đã xóa", "Sách \"" + book.getTitle() + "\" đã được xóa.");
            return true;
        }
        catch(Exception e) {
            showAlert(AlertType.ERROR, "Thất bại", "Không thể xóa sách. Vui lòng kiểm tra kết nối CSDL.");
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
