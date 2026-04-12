package com.example.project_oop.controller.publisher;

import com.example.project_oop.models.Publisher;
import com.example.project_oop.service.PublisherService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;

public class PublisherController {

    @FXML
    private TableView<Publisher> tblPublisher;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnAddPublisher;

    @FXML
    private TableColumn<Publisher, Void> colActions;

    private final PublisherService publisherService = new PublisherService();

    private final PublisherAddController publisherAddController = new PublisherAddController(publisherService);
    private final PublisherEditController publisherEditController = new PublisherEditController(publisherService);
    private final PublisherDeleteController publisherDeleteController = new PublisherDeleteController(publisherService);

    @FXML
    public void initialize(){

        System.out.println(">>> Giao diện Quản lý NXB đã sẵn sàng!");

        setupActionColumn();

        btnAddPublisher.setOnAction(event -> handleAddPublisher());

        // Thêm search listener
        tfSearch.textProperty().addListener((obs, oldVal, newVal) -> loadPublishers(newVal));

        loadPublishers("");
    }

    private void loadPublishers(String searchTerm){

        List<Publisher> allPublishers = publisherService.getAllPublishers();

        // Lọc theo tên NXB
        ObservableList<Publisher> filteredList = FXCollections.observableArrayList(
            allPublishers.stream()
                .filter(publisher -> publisher.getPublisherName()
                    .toLowerCase()
                    .contains(searchTerm.toLowerCase()))
                .toList()
        );

        tblPublisher.setItems(filteredList);
    }

    private void loadPublishers(){
        loadPublishers("");
    }

    private void setupActionColumn(){

        if (colActions == null) {
            System.err.println("ERROR: colActions is null - FXML binding issue");
            return;
        }

        colActions.setCellFactory(param -> new TableCell<>(){

            private final Button editBtn = new Button("Sửa");
            private final Button deleteBtn = new Button("Xóa");

            private final HBox pane = new HBox(6, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #2ecc71ff; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;");

                editBtn.setOnAction(event -> {

                    Publisher publisher = getTableView().getItems().get(getIndex());

                    if(publisherEditController.show(publisher))
                        loadPublishers();

                });

                deleteBtn.setOnAction(event -> {

                    Publisher publisher = getTableView().getItems().get(getIndex());

                    if(publisherDeleteController.show(publisher))
                        loadPublishers();

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
    public void handleAddPublisher(){

        if(publisherAddController.show())
            loadPublishers();

    }

}
