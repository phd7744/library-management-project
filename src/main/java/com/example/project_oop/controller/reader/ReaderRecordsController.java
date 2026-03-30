package com.example.project_oop.controller.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.service.ReaderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ReaderRecordsController {
    @FXML
    private TableView<Reader> readerTable;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private TableColumn<Reader, Integer>  colReaderId;
    @FXML
    private TableColumn<Reader, String>  colFullName;
    @FXML
    private TableColumn<Reader, String>  colPhone;
    @FXML
    private TableColumn<Reader, Double>  colDebt;
    @FXML
    private TableColumn<Reader, String>  colStatus;
    @FXML
    private TableColumn<Reader, Void> colAction;

    private final ReaderService readerService  = new ReaderService();
    private ObservableList<Reader> readerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colReaderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDebt.setCellValueFactory(new PropertyValueFactory<>("debt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        setupActionColumn();

        readerTable.setItems(readerList);
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
            stage.showAndWait();
            loadData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void handleEditReader(Reader reader){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_oop/fxml/edit-reader-view.fxml"));
            Parent root = loader.load();
            EditReaderController controller = loader.getController();
            controller.setReader(reader);
            Stage stage = new Stage();
            stage.setTitle("Register New Reader");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadData();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void loadData(){
        readerList.setAll(readerService.getAllReaders());
    }


    //ChatGPT
    private void setupActionColumn() {
        colAction.setCellFactory(param -> new TableCell<>() {

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Reader reader = getTableView().getItems().get(getIndex());

                    Button editBtn = new Button("Edit");
                    Button deleteBtn = new Button("Delete");

                    editBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
                    deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                    editBtn.setOnAction(e -> {
                        System.out.println("Edit: " + reader.getFullName());
                        handleEditReader(reader);
                    });

                    deleteBtn.setOnAction(e -> {
                        System.out.println("Delete: " + reader.getFullName());
                        try {
                            readerService.deleteReader(reader.getId());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        loadData();
                    });

                    setGraphic(new javafx.scene.layout.HBox(5, editBtn, deleteBtn));
                }
            }
        });
    }
}
