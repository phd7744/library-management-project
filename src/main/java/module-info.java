module com.example.project_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;

    requires org.kordamp.ikonli.javafx;
    requires transitive java.sql;
    requires java.desktop;

    opens com.example.project_oop to javafx.fxml;
    exports com.example.project_oop;
    exports com.example.project_oop.models;
    exports com.example.project_oop.repository;
    exports com.example.project_oop.repository.impl;

    opens com.example.project_oop.models to javafx.base;
    exports com.example.project_oop.controller;
    exports com.example.project_oop.service;
    
    opens com.example.project_oop.controller to javafx.fxml;
    exports com.example.project_oop.controller.reader;

    opens com.example.project_oop.controller.reader to javafx.fxml;
    exports com.example.project_oop.controller.borrow;

    opens com.example.project_oop.controller.borrow to javafx.fxml;
    exports com.example.project_oop.controller.book;
    
    opens com.example.project_oop.controller.book to javafx.fxml;
    exports com.example.project_oop.controller.category;
    
    opens com.example.project_oop.controller.category to javafx.fxml;
    exports com.example.project_oop.controller.author;
    
    opens com.example.project_oop.controller.author to javafx.fxml;
    exports com.example.project_oop.controller.publisher;

    opens com.example.project_oop.controller.publisher to javafx.fxml;
    exports com.example.project_oop.models.view;
    
    opens com.example.project_oop.models.view to javafx.base;
}