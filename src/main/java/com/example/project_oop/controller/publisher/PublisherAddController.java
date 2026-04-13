package com.example.project_oop.controller.publisher;

import com.example.project_oop.service.PublisherService;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.Optional;

public class PublisherAddController {

    private final PublisherService publisherService;

    public PublisherAddController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public boolean show() {

        Dialog<String[]> dialog = buildDialog(
                "Thêm NXB",
                "Nhập thông tin nhà xuất bản mới:",
                null,
                null
        );

        Optional<String[]> result = dialog.showAndWait();

        if (result.isEmpty()) return false;

        String[] data = result.get();
        String name = data[0];
        String address = data[1];

        if (publisherService.existsByName(name)) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Trùng tên",
                    "NXB \"" + name + "\" đã tồn tại!"
            );
            return false;
        }

        try {

            publisherService.addPublisher(name, address);

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Thành công",
                    "Đã thêm NXB \"" + name + "\"!"
            );

            return true;

        } catch (SQLException | RuntimeException e) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Thất bại",
                    "Không thể thêm NXB. Vui lòng kiểm tra kết nối CSDL."
            );

            return false;
        }
    }

    static Dialog<String[]> buildDialog(String title, String header, String prefillName, String prefillAddress) {

        Dialog<String[]> dialog = new Dialog<>();

        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType confirmType = new ButtonType("Lưu", ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(
                confirmType,
                ButtonType.CANCEL
        );

        TextField tfName = new TextField();
        tfName.setPromptText("NXB Kim Đồng");
        tfName.setPrefWidth(250);

        if (prefillName != null)
            tfName.setText(prefillName);

        TextField tfAddress = new TextField();
        tfAddress.setPromptText("Địa chỉ NXB");
        tfAddress.setPrefWidth(250);

        if (prefillAddress != null)
            tfAddress.setText(prefillAddress);

        GridPane grid = new GridPane();

        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 20));

        grid.add(new Label("Tên NXB (*):"), 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(new Label("Địa chỉ:"), 0, 1);
        grid.add(tfAddress, 1, 1);

        dialog.getDialogPane().setContent(grid);

        javafx.scene.Node confirmBtn =
                dialog.getDialogPane().lookupButton(confirmType);

        confirmBtn.setDisable(tfName.getText() == null || tfName.getText().isBlank());

        tfName.textProperty().addListener((obs, old, val) ->
                confirmBtn.setDisable(val == null || val.isBlank())
        );

        dialog.setResultConverter(
                pressed -> pressed == confirmType
                        ? new String[]{tfName.getText().trim(), tfAddress.getText().trim()}
                        : null
        );

        return dialog;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
