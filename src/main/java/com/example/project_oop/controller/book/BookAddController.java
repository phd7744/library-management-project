package com.example.project_oop.controller.book;

import com.example.project_oop.models.Book;
import com.example.project_oop.service.BookService;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.util.Optional;

public class BookAddController{

    private final BookService bookService;

    public BookAddController(BookService bookService){
        this.bookService = bookService;
    }

    public boolean show(){
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Thêm Sách Mới");
        dialog.setHeaderText("Nhập thông tin sách:");

        ButtonType addButtonType = new ButtonType("Thêm Sách", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        BookForm form = new BookForm();
        form.applyStatusRule("ACTIVE");
        dialog.getDialogPane().setContent(form.buildGrid());

        javafx.scene.Node addBtn = dialog.getDialogPane().lookupButton(addButtonType);
        form.bindValidation(addBtn);

        dialog.setResultConverter(pressed -> {
            if (pressed == addButtonType){
                return new Book(0,
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
        if (result.isEmpty()) return false;

        try {
            bookService.addBook(result.get());
            showAlert(Alert.AlertType.INFORMATION, "Thành công",
                    "Sách \"" + result.get().getTitle() + "\" đã được thêm!");
            return true;
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Thất bại",
                    "Không thể thêm sách. Vui lòng kiểm tra kết nối CSDL.");
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

    // BookForm
    static final class BookForm {
        final TextField tfIsbn = new TextField();
        final TextField tfTitle = new TextField();
        final TextField tfAuthor = new TextField();
        final TextField tfPublisher = new TextField();
        final TextField tfCategory  = new TextField();
        final Spinner<Integer> spPublishYear;
        final Spinner<Integer> spQuantity;
        final ComboBox<String> cbStatus = new ComboBox<>();
        final Label lblQuantity = new Label("Số lượng:");

        BookForm() {
            int currentYear = java.time.Year.now().getValue();
            spPublishYear = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, currentYear, currentYear));
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
            tfAuthor.setPromptText("Nguyễn Văn A");
            tfPublisher.setPromptText("NXB Đại học Quốc gia");
            tfCategory.setPromptText("Khoa học máy tính");

            cbStatus.valueProperty().addListener((obs, o, newVal) -> applyStatusRule(newVal));
        }

        GridPane buildGrid() {
            GridPane grid = new GridPane();
            grid.setHgap(12);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 60, 10, 20));

            grid.add(new Label("ISBN (*):"), 0, 0); grid.add(tfIsbn, 1, 0);
            grid.add(new Label("Tiêu đề (*):"), 0, 1); grid.add(tfTitle, 1, 1);
            grid.add(new Label("Tác giả (*):"), 0, 2); grid.add(tfAuthor, 1, 2);
            grid.add(new Label("Nhà xuất bản:"), 0, 3); grid.add(tfPublisher, 1, 3);
            grid.add(new Label("Thể loại (*):"), 0, 4); grid.add(tfCategory, 1, 4);
            grid.add(new Label("Năm xuất bản:"), 0, 5); grid.add(spPublishYear, 1, 5);
            grid.add(new Label("Trạng thái:"), 0, 6); grid.add(cbStatus, 1, 6);
            grid.add(lblQuantity, 0, 7); grid.add(spQuantity, 1, 7);
            return grid;
        }

        void applyStatusRule(String status) {
            boolean isActive = "ACTIVE".equals(status);
            lblQuantity.setVisible(isActive);
            lblQuantity.setManaged(isActive);
            spQuantity.setVisible(isActive);
            spQuantity.setManaged(isActive);
            if (!isActive) spQuantity.getValueFactory().setValue(0);
        }

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

        void bindValidation(javafx.scene.Node confirmBtn) {
            confirmBtn.setDisable(true);
            Runnable check = () -> confirmBtn.setDisable(
                tfIsbn.getText().isBlank() || tfTitle.getText().isBlank() ||
                tfAuthor.getText().isBlank() || tfCategory.getText().isBlank() ||
                spQuantity.getValue() <= 0 || spPublishYear.getValue() <= 0 ||
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
            return "INACTIVE".equals(cbStatus.getValue()) ? 0 : spQuantity.getValue();
        }

        String getStatus() {
            return cbStatus.getValue();
        }
    }
}
