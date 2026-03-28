module com.example.project_oop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.project_oop to javafx.fxml;
    exports com.example.project_oop;
    exports com.example.project_oop.models;

    opens com.example.project_oop.models to javafx.base;
    exports com.example.project_oop.controller;
    
    opens com.example.project_oop.controller to javafx.fxml;
}