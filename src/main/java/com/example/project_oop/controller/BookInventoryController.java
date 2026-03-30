package com.example.project_oop.controller;

import com.example.project_oop.models.Book;
import com.example.project_oop.utils.BookDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Optional;

public class BookInventoryController {

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

    private final com.example.project_oop.service.BookService bookService = new com.example.project_oop.service.BookService();
    private final ObservableList<Book> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
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

        // Category Filter
        ObservableList<String> categories = FXCollections.observableArrayList("All Categories");
        com.example.project_oop.utils.CategoryDAO.getAllCategories().forEach(c -> categories.add(c.getName()));
        cbCategoryFilter.setItems(categories);
        cbCategoryFilter.setValue("All Categories");

        // Status Filter
        cbStatusFilter.setItems(FXCollections.observableArrayList("All Status", "ACTIVE", "INACTIVE"));
        cbStatusFilter.setValue("All Status");

        bookService.setupSearchFilter(tfSearch, cbCategoryFilter, cbStatusFilter, btnClearFilters, masterData, bookTableView);
        loadBooks();
    }

    // Edit + Delete
    private void setupActionColumn() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(6, editBtn, deleteBtn);
            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                /* Edit */
                editBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    handleEditBook(book);
                });

                /* Delete */
                deleteBtn.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    BookDAO.deleteBook(book.getId());
                    loadBooks();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void loadBooks() {
        masterData.setAll(BookDAO.getAllBooks());
    }

    // Add & Edit
    private static final class BookForm {
        final TextField tfIsbn = new TextField();
        final TextField tfTitle = new TextField();
        final TextField tfAuthor = new TextField();
        final TextField tfPublisher = new TextField();
        final TextField tfCategory = new TextField();
        final Spinner<Integer> spPublishYear;
        final Spinner<Integer> spQuantity;
        final ComboBox<String> cbStatus = new ComboBox<>();
        final Label lblQuantity = new Label("Số lượng:");
        final GridPane grid = new GridPane();

        BookForm() {
            int currentYear = java.time.Year.now().getValue();
            spPublishYear = new Spinner<>(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, currentYear, currentYear));
            spPublishYear.setEditable(true);
            spPublishYear.setPrefWidth(120);

            spQuantity = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 1));
            spQuantity.setEditable(true);
            spQuantity.setPrefWidth(100);

            cbStatus.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE"));
            cbStatus.setValue("ACTIVE");
            cbStatus.setPrefWidth(120);

            tfIsbn.setPromptText("978-3-16-148410-0");
            tfTitle.setPromptText("Lập trình Java cơ bản");
            tfAuthor.setPromptText("Nguyễn Hữu Cầm");
            tfPublisher.setPromptText("NXB Học viện Công nghệ Bưu chính Viễn thông");
            tfCategory.setPromptText("Kỹ thuật dữ liệu");

            grid.setHgap(12);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 60, 10, 20));

            grid.add(new Label("ISBN (*):"), 0, 0);
            grid.add(tfIsbn, 1, 0);
            grid.add(new Label("Tiêu đề (*):"), 0, 1);
            grid.add(tfTitle, 1, 1);
            grid.add(new Label("Tác giả (*):"), 0, 2);
            grid.add(tfAuthor, 1, 2);
            grid.add(new Label("Nhà xuất bản:"), 0, 3);
            grid.add(tfPublisher, 1, 3);
            grid.add(new Label("Thể loại (*):"), 0, 4);
            grid.add(tfCategory, 1, 4);
            grid.add(new Label("Năm xuất bản:"), 0, 5);
            grid.add(spPublishYear, 1, 5);
            grid.add(new Label("Trạng thái:"), 0, 6);
            grid.add(cbStatus, 1, 6);
            grid.add(lblQuantity, 0, 7);
            grid.add(spQuantity, 1, 7);

            cbStatus.valueProperty().addListener((obs, oldVal, newVal) -> applyStatusRule(newVal));
        }

        /**
         * Status:
         * - INACTIVE → ẩn spinner, quantity = 0
         * - ACTIVE → hiện spinner
         */
        void applyStatusRule(String status) {
            boolean isActive = "ACTIVE".equals(status);
            lblQuantity.setVisible(isActive);
            lblQuantity.setManaged(isActive);
            spQuantity.setVisible(isActive);
            spQuantity.setManaged(isActive);
            if (!isActive) {
                spQuantity.getValueFactory().setValue(0);
            }
        }

        /* Pre-fill form (Edit) */
        void fillFrom(Book book) {
            tfIsbn.setText(book.getIsbn() == null ? "" : book.getIsbn());
            tfTitle.setText(book.getTitle());
            tfAuthor.setText(book.getAuthor());
            tfPublisher.setText(book.getPublisher() == null ? "" : book.getPublisher());
            tfCategory.setText(book.getCategory());
            int py = book.getPublishYear();
            spPublishYear.getValueFactory().setValue(py > 0 ? py : java.time.Year.now().getValue());
            String st = book.getStatus();
            cbStatus.setValue((st != null && st.equals("INACTIVE")) ? "INACTIVE" : "ACTIVE");
            applyStatusRule(cbStatus.getValue());

            if ("ACTIVE".equals(cbStatus.getValue())) {
                spQuantity.getValueFactory().setValue(book.getQuantity() > 0 ? book.getQuantity() : 1);
            }
        }

        /* Confirm validation */
        void bindValidation(javafx.scene.Node confirmBtn) {
            confirmBtn.setDisable(true);
            Runnable check = () -> confirmBtn.setDisable(
                tfIsbn.getText().isBlank() ||
                    tfTitle.getText().isBlank() ||
                    tfAuthor.getText().isBlank() ||
                    tfCategory.getText().isBlank() ||
                    spQuantity.getValue() <= 0 ||
                    spPublishYear.getValue() <= 0 ||
                    cbStatus.getValue().isBlank()
            );
            tfIsbn.textProperty().addListener((o, v1, v2) -> check.run());
            tfTitle.textProperty().addListener((o, v1, v2) -> check.run());
            tfAuthor.textProperty().addListener((o, v1, v2) -> check.run());
            tfCategory.textProperty().addListener((o, v1, v2) -> check.run());
            spQuantity.valueProperty().addListener((o, v1, v2) -> check.run());
            spPublishYear.valueProperty().addListener((o, v1, v2) -> check.run());
            cbStatus.valueProperty().addListener((o, v1, v2) -> check.run());
        }

        int getQuantity() {
            return cbStatus.getValue().equals("INACTIVE") ? 0 : spQuantity.getValue();
        }

        String getStatus() {
            return cbStatus.getValue();
        }
    }

    /* Add */
    @FXML
    private void handleAddBook() {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Thêm Sách Mới");
        dialog.setHeaderText("Nhập thông tin sách:");

        ButtonType addButtonType = new ButtonType("Thêm Sách", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        BookForm form = new BookForm();

        form.applyStatusRule("ACTIVE");
        dialog.getDialogPane().setContent(form.grid);

        javafx.scene.Node addBtn = dialog.getDialogPane().lookupButton(addButtonType);
        form.bindValidation(addBtn);

        dialog.setResultConverter(pressed -> {
            if (pressed == addButtonType) {
                return new Book(0,
                    form.tfIsbn.getText().trim(),
                    form.tfTitle.getText().trim(),
                    form.tfCategory.getText().trim(),
                    form.tfAuthor.getText().trim(),
                    form.tfPublisher.getText().trim(),
                    form.spPublishYear.getValue(),
                    form.getQuantity(),
                    form.getStatus());
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(book -> {
            boolean success = BookDAO.addBook(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getCategory(),
                book.getPublishYear(),
                book.getQuantity()
            );
            if (success) {
                loadBooks();
                showAlert(AlertType.INFORMATION, "Thành công",
                        "Sách \"" + book.getTitle() + "\" đã được thêm vào danh sách!");
            } else {
                showAlert(AlertType.ERROR, "Thất bại", "Không thể thêm sách. Vui lòng kiểm tra kết nối CSDL.");
            }
        });
    }

    /* Edit */
    private void handleEditBook(Book book) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh Sửa Sách");
        dialog.setHeaderText("Cập nhật thông tin sách (ID: " + book.getId() + ")");

        ButtonType saveButtonType = new ButtonType("Lưu Thay Đổi", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        BookForm form = new BookForm();
        form.fillFrom(book);
        dialog.getDialogPane().setContent(form.grid);

        javafx.scene.Node saveBtn = dialog.getDialogPane().lookupButton(saveButtonType);
        form.bindValidation(saveBtn);

        dialog.setResultConverter(pressed -> {
            if (pressed == saveButtonType) {
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
        result.ifPresent(updated -> {
            boolean success = BookDAO.updateBook(
                    updated.getId(),
                    updated.getIsbn(),
                    updated.getTitle(),
                    updated.getAuthor(),
                    updated.getPublisher(),
                    updated.getCategory(),
                    updated.getPublishYear(),
                    updated.getQuantity(),
                    updated.getStatus());
            if (success) {
                loadBooks();
                showAlert(AlertType.INFORMATION, "Thành công", "Đã cập nhật sách \"" + updated.getTitle() + "\"!");
            } else {
                showAlert(AlertType.ERROR, "Thất bại", "Không thể cập nhật sách. Vui lòng kiểm tra kết nối CSDL.");
            }
        });
    }

    /* Hiển thị thông báo. */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
