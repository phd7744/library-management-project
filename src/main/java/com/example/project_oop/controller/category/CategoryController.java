package com.example.project_oop.controller.category;

import com.example.project_oop.models.Category;
import com.example.project_oop.service.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;


public class CategoryController {

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, Integer> colCatId;

    @FXML
    private TableColumn<Category, String> colCatName;

    @FXML
    private TableColumn<Category, Void> colCatAction;

    private final CategoryService categoryService = new CategoryService();

    private final CategoryAddController categoryAddController = new CategoryAddController(categoryService);
    private final CategoryEditController categoryEditController = new CategoryEditController(categoryService);
    private final CategoryDeleteController categoryDeleteController = new CategoryDeleteController(categoryService);

    @FXML
    public void initialize(){
        System.out.println(">>> Giao diện Quản lý Thể loại đã sẵn sàng!");
        colCatId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCatName.setCellValueFactory(new PropertyValueFactory<>("name"));
        setupActionColumn();
        loadCategories();
    }

    private void loadCategories(){
        ObservableList<Category> list = FXCollections.observableArrayList(categoryService.getAllCategories());
        categoryTableView.setItems(list);
    }

    private void setupActionColumn() {
        colCatAction.setCellFactory(param -> new TableCell<>(){
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(6, editBtn, deleteBtn);
            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {
                    Category cat = getTableView().getItems().get(getIndex());
                    if (categoryEditController.show(cat)) loadCategories();
                });

                deleteBtn.setOnAction(event -> {
                    Category cat = getTableView().getItems().get(getIndex());
                    if (categoryDeleteController.show(cat)) loadCategories();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    @FXML
    private void handleAddCategory(){
        if (categoryAddController.show()) loadCategories();
    }
}
