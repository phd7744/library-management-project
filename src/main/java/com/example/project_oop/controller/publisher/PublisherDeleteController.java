package com.example.project_oop.controller.publisher;

import com.example.project_oop.models.Publisher;
import com.example.project_oop.service.PublisherService;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Optional;

public class PublisherDeleteController {

    private final PublisherService publisherService;

    public PublisherDeleteController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public boolean show(Publisher publisher) {

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Xóa NXB");
        confirmDialog.setHeaderText("Xóa nhà xuất bản?");
        confirmDialog.setContentText("Bạn có chắc chắn muốn xóa NXB \"" + publisher.getPublisherName() + "\"?");

        Optional<ButtonType> result = confirmDialog.showAndWait();

        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return false;
        }

        try {

            publisherService.deletePublisher(publisher.getPublisherId());

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Thành công");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Đã xóa NXB \"" + publisher.getPublisherName() + "\"!");
            successAlert.showAndWait();

            return true;

        } catch (IllegalStateException e) {

            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Không thể xóa");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText(
                "Nhà xuất bản \"" + publisher.getPublisherName() + "\" đang được dùng bởi một hoặc nhiều sách.\n"
                + "Vui lòng chuyển các sách sang nhà xuất bản khác trước khi xóa."
            );
            warningAlert.showAndWait();

            return false;

        } catch (SQLException | RuntimeException e) {

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Thất bại");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Không thể xóa NXB. Vui lòng kiểm tra kết nối CSDL.");
            errorAlert.showAndWait();

            return false;
        }
    }
}
