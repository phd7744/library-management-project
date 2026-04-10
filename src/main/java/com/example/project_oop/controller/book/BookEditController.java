package com.example.project_oop.controller.book;

import com.example.project_oop.models.Book;
import com.example.project_oop.service.BookService;
import com.example.project_oop.controller.book.BookAddController.BookForm;

import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.Optional;

public class BookEditController{

    private final BookService bookService;

    public BookEditController(BookService bookService){
        this.bookService = bookService;
    }

    public boolean show(Book book){
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh Sửa Sách");
        dialog.setHeaderText("Cập nhật thông tin sách (ID: " + book.getId() + ")");

        ButtonType saveButtonType = new ButtonType("Lưu Thay Đổi", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        BookForm form = new BookForm();
        form.fillFrom(book);
        dialog.getDialogPane().setContent(form.buildGrid());

        javafx.scene.Node saveBtn = dialog.getDialogPane().lookupButton(saveButtonType);
        form.bindValidation(saveBtn);

        dialog.setResultConverter(pressed -> {
            if (pressed == saveButtonType){
                return new Book(
                    book.getId(),
                    form.tfIsbn.getText().trim(),
                    form.tfTitle.getText().trim(),
                    form.tfCategory.getText().trim(),
                    form.tfAuthor.getText().trim(),
                    form.tfPublisher.getText().trim(),
                    form.spPublishYear.getValue(),
                    form.getQuantity(),
                    form.getStatus()
                );
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        if(result.isEmpty()) return false;

        try{
            bookService.updateBook(result.get());
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật sách \"" + result.get().getTitle() + "\"!");
            return true;
        }
        catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể cập nhật sách. Vui lòng kiểm tra kết nối CSDL.");
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
