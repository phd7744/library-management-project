package com.example.project_oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
        MainApp.class.getResource("/com/example/project_oop/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 960);
    stage.setTitle("Quan Ly Thu Vien");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}