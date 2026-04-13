package com.example.project_oop.controller.author;

import com.example.project_oop.models.Author;
import com.example.project_oop.service.AuthorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

public class AuthorController {

    @FXML
    private TableView<Author> authorTableView;

    @FXML
    private TableColumn<Author, Integer> colAuthorId;

    @FXML
    private TableColumn<Author, String> colAuthorName;

    @FXML
    private TableColumn<Author, Void> colAuthorAction;

    @FXML
    private TextField tfSearch;

    private final AuthorService authorService = new AuthorService();

    private final AuthorAddController authorAddController = new AuthorAddController(authorService);
    private final AuthorEditController authorEditController = new AuthorEditController(authorService);
    private final AuthorDeleteController authorDeleteController = new AuthorDeleteController(authorService);

    @FXML
    public void initialize(){

        System.out.println(">>> Giao diện Quản lý Tác giả đã sẵn sàng!");

        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));

        setupActionColumn();

        // Thêm search listener
        tfSearch.textProperty().addListener((obs, oldVal, newVal) -> loadAuthors(newVal));

        loadAuthors("");
    }

    private void loadAuthors(String searchTerm){

        List<Author> allAuthors = authorService.getAllAuthors();

        // Lọc theo tên tác giả
        ObservableList<Author> filteredList = FXCollections.observableArrayList(
            allAuthors.stream()
                .filter(author -> author.getAuthorName()
                    .toLowerCase()
                    .contains(searchTerm.toLowerCase()))
                .toList()
        );

        authorTableView.setItems(filteredList);
    }

    private void loadAuthors(){
        loadAuthors("");
    }

    private void setupActionColumn(){

        colAuthorAction.setCellFactory(param -> new TableCell<>(){

            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");

            private final HBox pane = new HBox(6, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {

                    Author author = getTableView().getItems().get(getIndex());

                    if(authorEditController.show(author))
                        loadAuthors();

                });

                deleteBtn.setOnAction(event -> {

                    Author author = getTableView().getItems().get(getIndex());

                    if(authorDeleteController.show(author))
                        loadAuthors();

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){

                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);

            }

        });

    }

    @FXML
    public void handleAddAuthor(){

        if(authorAddController.show())
            loadAuthors();

    }

}
