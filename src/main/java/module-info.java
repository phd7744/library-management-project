module com.example.project_oop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.logging;

    opens com.example.project_oop to javafx.fxml;
    exports com.example.project_oop;
    exports com.example.project_oop.controller;
    opens com.example.project_oop.controller to javafx.fxml;
}