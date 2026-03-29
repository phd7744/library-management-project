package com.example.project_oop.controller.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.ReaderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditReaderController {
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private TextField txtDebt;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPhone;

    private int currentReaderId;
    private final ReaderService readerService = new ReaderService();

    @FXML
    public void initialize() {
        // Khởi tạo các giá trị cho ComboBox
        cbStatus.getItems().addAll("ACTIVE", "INACTIVE", "BANNED");
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void handleSaveChanges(ActionEvent event) {
        Reader reader = new Reader();
        reader.setId(currentReaderId);
        reader.setFullName(txtFullName.getText());
        reader.setPhone(txtPhone.getText());
        reader.setDebt(Double.parseDouble(txtDebt.getText()));
        reader.setStatus(cbStatus.getValue());

        try {
            readerService.updateReader(reader);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeWindow();
    }

    public void setReader(Reader reader) {
        this.currentReaderId = reader.getId();

        txtFullName.setText(reader.getFullName());
        txtPhone.setText(reader.getPhone());
        txtDebt.setText( String.valueOf(reader.getDebt()));
        cbStatus.setValue(reader.getStatus());
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
