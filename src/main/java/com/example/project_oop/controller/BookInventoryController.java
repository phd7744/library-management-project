package com.example.project_oop.controller;

import com.example.project_oop.models.Book;
import com.example.project_oop.utils.BookDAO;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
    public void initialize(){
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

    private void setupActionColumn(){
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

    private void loadBooks(){
        ObservableList<Book> books = BookDAO.getAllBooks();
        bookTableView.setItems(books);
    }

    @FXML
    private void handleAddBook() {
        // --- Tạo Dialog tùy chỉnh ---
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Thêm Sách Mới");
        dialog.setHeaderText("Nhập thông tin sách:");

        ButtonType addButtonType = new ButtonType("Thêm Sách", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // --- Các trường nhập liệu ---
        TextField tfTitle = new TextField();
        tfTitle.setPromptText("Ví dụ: Lập trình Java cơ bản");

        TextField tfAuthor = new TextField();
        tfAuthor.setPromptText("Ví dụ: Nguyễn Hữu Cầm");

        TextField tfPublisher = new TextField();
        tfPublisher.setPromptText("Ví dụ: NXB Học viện Công nghệ Bưu chính Viễn thông");

        TextField tfCategory = new TextField();
        tfCategory.setPromptText("Ví dụ: Kỹ thuật dữ liệu");

        int currentYear = java.time.Year.now().getValue();
        Spinner<Integer> spPublishYear = new Spinner<>();
        spPublishYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, currentYear, currentYear));
        spPublishYear.setEditable(true);
        spPublishYear.setPrefWidth(120);

        Spinner<Integer> spQuantity = new Spinner<>();
        spQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999, 1));
        spQuantity.setEditable(true);
        spQuantity.setPrefWidth(100);

        // --- Sắp xếp vào GridPane ---
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 20));

        grid.add(new Label("Tiêu đề (*) :"), 0, 0); grid.add(tfTitle, 1, 0);
        grid.add(new Label("Tác giả (*) :"), 0, 1); grid.add(tfAuthor, 1, 1);
        grid.add(new Label("Nhà xuất bản:"), 0, 2); grid.add(tfPublisher, 1, 2);
        grid.add(new Label("Thể loại (*):"), 0, 3); grid.add(tfCategory, 1, 3);
        grid.add(new Label("Năm xuất bản:"), 0, 4); grid.add(spPublishYear, 1, 4);
        grid.add(new Label("Số lượng:"), 0, 5); grid.add(spQuantity, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Disable nút "Thêm Sách" khi các trường bắt buộc (’*’) còn trống
        javafx.scene.Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);
        tfTitle.textProperty().addListener((obs, old, val) -> addButton.setDisable(val.isBlank() || tfAuthor.getText().isBlank() || tfCategory.getText().isBlank()));
        tfAuthor.textProperty().addListener((obs, old, val) -> addButton.setDisable(val.isBlank() || tfTitle.getText().isBlank() || tfCategory.getText().isBlank()));
        tfCategory.textProperty().addListener((obs, old, val) -> addButton.setDisable(val.isBlank() || tfTitle.getText().isBlank() || tfAuthor.getText().isBlank()));

        // --- Chuyển đổi kết quả sang đối tượng Book tạm thời ---
        dialog.setResultConverter(buttonPressed -> {
            if(buttonPressed == addButtonType){
                return new Book(0,
                    tfTitle.getText().trim(),
                    tfCategory.getText().trim(),
                    tfAuthor.getText().trim(),
                    tfPublisher.getText().trim(),
                    spPublishYear.getValue(),
                    spQuantity.getValue(),
                    "ACTIVE");
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(book -> {
            boolean success = BookDAO.addBook(
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getCategory(),
                book.getPublishYear(),
                book.getQuantity()
            );

            if (success) {
                loadBooks();
                showAlert(AlertType.INFORMATION, "Thành công", "Sách \"" + book.getTitle() + "\" đã được thêm vào danh sách!");
            }
            else {
                showAlert(AlertType.ERROR, "Thất bại", "Không thể thêm sách. Vui lòng kiểm tra kết nối CSDL.");
            }
        });
    }

    /** Hiển thị hộp thoại thông báo đơn giản. */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
