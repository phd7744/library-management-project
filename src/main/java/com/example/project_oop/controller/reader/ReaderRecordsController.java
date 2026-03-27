package com.example.project_oop.controller.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.reader.ReaderService;
import com.example.project_oop.service.reader.ReaderServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReaderRecordsController {
    @FXML
    private TableView<Reader> readerTable;

    @FXML
    private TableColumn<Reader, Integer>  colReaderId;
    @FXML
    private TableColumn<Reader, String>  colFullName;
    @FXML
    private TableColumn<Reader, String>  colPhone;
    @FXML
    private TableColumn<Reader, String>  colType;
    @FXML
    private TableColumn<Reader, Double>  colDebt;
    @FXML
    private TableColumn<Reader, String>  colStatus;

    private final ReaderService readerService = new ReaderServiceImpl();

    @FXML
    public void initialize() {

        colReaderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDebt.setCellValueFactory(new PropertyValueFactory<>("debt"));

        loadData();
    }

    @FXML
    public void handleAddNewReader(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/add-reader-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Register New Reader");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void loadData(){
        readerTable.getItems().setAll(readerService.getAllReaders());
    }
}
