package com.example.project_oop.controller;

import com.example.project_oop.models.Category;
import com.example.project_oop.utils.CategoryDAO;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Optional;

public class CategoryController{

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, Integer> colCatId;

    @FXML
    private TableColumn<Category, String> colCatName;

    @FXML
    private TableColumn<Category, Void> colCatAction;

    @FXML
    public void initialize(){
        System.out.println(">>> Giao diện Quản lý Thể loại đã sẵn sàng!");
        colCatId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCatName.setCellValueFactory(new PropertyValueFactory<>("name"));
        setupActionColumn();
        loadCategories();
    }

    private void loadCategories(){
        ObservableList<Category> categories = CategoryDAO.getAllCategories();
        categoryTableView.setItems(categories);
    }

    // Edit + Delete
    private void setupActionColumn(){
        colCatAction.setCellFactory(param -> new TableCell<>(){
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(6, editBtn, deleteBtn);
            {
                editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {
                    Category cat = getTableView().getItems().get(getIndex());
                    handleEditCategory(cat);
                });

                deleteBtn.setOnAction(event -> {
                    Category cat = getTableView().getItems().get(getIndex());
                    handleDeleteCategory(cat);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    // Add
    @FXML
    private void handleAddCategory(){
        Dialog<String> dialog = buildNameDialog("Thêm Thể Loại", "Nhập tên thể loại mới:", null);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if(CategoryDAO.existsByName(name)){
                showAlert(AlertType.WARNING, "Trùng tên", "Thể loại \"" + name + "\" đã tồn tại!");
                return;
            }
            boolean success = CategoryDAO.addCategory(name);
            if(success){
                loadCategories();
                showAlert(AlertType.INFORMATION, "Thành công", "Đã thêm thể loại \"" + name + "\"!");
            }
            else{
                showAlert(AlertType.ERROR, "Thất bại", "Không thể thêm thể loại. Vui lòng kiểm tra kết nối CSDL.");
            }
        });
    }

    // Edit
    private void handleEditCategory(Category cat){
        Dialog<String> dialog = buildNameDialog(
                "Chỉnh Sửa Thể Loại",
                "Cập nhật tên thể loại (ID: " + cat.getId() + "):",
                cat.getName()
        );
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            if(newName.equals(cat.getName())) return; // không thay đổi
            if(CategoryDAO.existsByName(newName)){
                showAlert(AlertType.WARNING, "Trùng tên", "Thể loại \"" + newName + "\" đã tồn tại!");
                return;
            }
            boolean success = CategoryDAO.updateCategory(cat.getId(), newName);
            if(success){
                loadCategories();
                showAlert(AlertType.INFORMATION, "Thành công", "Đã đổi tên thành \"" + newName + "\"!");
            }
            else{
                showAlert(AlertType.ERROR, "Thất bại", "Không thể cập nhật thể loại. Vui lòng kiểm tra kết nối CSDL.");
            }
        });
    }

    // Delete
    private void handleDeleteCategory(Category cat){
        // Xác nhận trước khi xóa
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa thể loại \"" + cat.getName() + "\" không?\n" + "Thao tác này không thể hoàn tác.");
        Optional<ButtonType> choice = confirm.showAndWait();

        if(choice.isEmpty() || choice.get() != ButtonType.OK) return;

        boolean success = CategoryDAO.deleteCategory(cat.getId());
        if(success){
            loadCategories();
            showAlert(AlertType.INFORMATION, "Đã xóa", "Thể loại \"" + cat.getName() + "\" đã được xóa.");
        }
        else{
            showAlert(AlertType.WARNING, "Không thể xóa", "Thể loại \"" + cat.getName() + "\" đang được dùng bởi một hoặc nhiều sách.\n" + "Vui lòng chuyển các sách sang thể loại khác trước khi xóa.");
        }
    }

    // Tạo Dialog nhập
    private Dialog<String> buildNameDialog(String title, String header, String prefillValue){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType confirmType = new ButtonType("Lưu", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmType, ButtonType.CANCEL);

        TextField tfName = new TextField();
        tfName.setPromptText("Khoa học máy tính");
        tfName.setPrefWidth(250);
        if(prefillValue != null) tfName.setText(prefillValue);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 20));
        grid.add(new Label("Tên thể loại (*):"), 0, 0);
        grid.add(tfName, 1, 0);
        dialog.getDialogPane().setContent(grid);

        // Disable nút Lưu khi trường trống
        javafx.scene.Node confirmBtn = dialog.getDialogPane().lookupButton(confirmType);
        confirmBtn.setDisable(prefillValue == null || prefillValue.isBlank());
        tfName.textProperty().addListener((obs, old, val) -> confirmBtn.setDisable(val == null || val.isBlank()));

        dialog.setResultConverter(pressed -> pressed == confirmType ? tfName.getText().trim() : null);

        return dialog;
    }

    // Hiện Alert
    private void showAlert(AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
