package com.example.project_oop.controller.book;

import com.example.project_oop.models.Book;
import com.example.project_oop.service.BookService;
import com.example.project_oop.service.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;


public class BookInventoryController{
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colIsbn;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colPublisher;

    @FXML
    private TableColumn<Book, Integer> colPublishYear;

    @FXML
    private TableColumn<Book, Integer> colQuantity;

    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML
    private TableColumn<Book, Void> colAction;

    @FXML
    private TextField tfSearch;

    @FXML
    private ComboBox<String> cbCategoryFilter;

    @FXML
    private ComboBox<String> cbStatusFilter;

    @FXML
    private Button btnClearFilters;

    private final BookService bookService = new BookService();
    private final CategoryService categoryService = new CategoryService();

    private final BookAddController bookAddController = new BookAddController(bookService);
    private final BookEditController bookEditController = new BookEditController(bookService);
    private final BookDeleteController bookDeleteController = new BookDeleteController(bookService);
    private final BookSearchFilterController bookSearchFilterController = new BookSearchFilterController();

    private final ObservableList<Book> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        System.out.println(">>> Giao diện Quản lý Sách đã sẵn sàng!");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colPublishYear.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupActionColumn();

        ObservableList<String> categoryNames = FXCollections.observableArrayList("All Categories");
        categoryService.getAllCategories().forEach(c -> categoryNames.add(c.getName()));
        cbCategoryFilter.setItems(categoryNames);
        cbCategoryFilter.setValue("All Categories");

        cbStatusFilter.setItems(FXCollections.observableArrayList("All Status", "ACTIVE", "INACTIVE"));
        cbStatusFilter.setValue("All Status");

        bookSearchFilterController.setup(tfSearch, cbCategoryFilter, cbStatusFilter, btnClearFilters, masterData, bookTableView);
        loadBooks();
    }

    private void loadBooks(){
        masterData.setAll(bookService.getAllBooks());
    }
    private void setupActionColumn(){
        colAction.setCellFactory(param -> new TableCell<>(){
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(6, editBtn, deleteBtn);
            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    if(bookEditController.show(book)) loadBooks();
                });

                deleteBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    if(bookDeleteController.show(book)) loadBooks();
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
    private void handleAddBook(){
        if(bookAddController.show()) loadBooks();
    }
}
