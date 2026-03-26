package com.example.project_oop.controller;

import com.example.project_oop.models.Book;
import com.example.project_oop.utils.BookDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Optional;

public class BookInventoryController {

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colPublisher;

    @FXML
    private TableColumn<Book, Integer> colQuantity;

    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML
    private TableColumn<Book, Void> colAction;

    @FXML
    public void initialize() {
        System.out.println("Giao diện Quản lý Sách đã sẵn sàng!");
        
        // Ánh xạ cột thuộc tính
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupActionColumn();
        loadBooks();
    }

    private void setupActionColumn() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                deleteBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    BookDAO.deleteBook(book.getId());
                    loadBooks();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void loadBooks() {
        ObservableList<Book> books = BookDAO.getAllBooks();
        bookTableView.setItems(books);
    }

    @FXML
    private void handleAddBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Thêm Sách mới");
        dialog.setHeaderText("Nhập nhanh tiêu đề sách mới:");
        dialog.setContentText("Tiêu đề:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(title -> {
            BookDAO.addBook(title, 0, "ACTIVE");
            loadBooks();
        });
    }
}
