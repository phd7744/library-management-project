module com.example.project_oop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens com.example.project_oop to javafx.fxml;
    exports com.example.project_oop;
    exports com.example.project_oop.models;

    opens com.example.project_oop.models to javafx.base;
    exports com.example.project_oop.controller;
    
    opens com.example.project_oop.controller to javafx.fxml;
    exports com.example.project_oop.controller.reader;
    opens com.example.project_oop.controller.reader to javafx.fxml;
    exports com.example.project_oop.controller.borrow;
    opens com.example.project_oop.controller.borrow to javafx.fxml;
    exports com.example.project_oop.models.view;
    opens com.example.project_oop.models.view to javafx.base;
}