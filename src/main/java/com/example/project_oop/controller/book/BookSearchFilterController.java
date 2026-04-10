package com.example.project_oop.controller.book;

import com.example.project_oop.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BookSearchFilterController{
    public void setup(TextField searchField, ComboBox<String> cbCategory, ComboBox<String> cbStatus, Button btnClear, ObservableList<Book> masterData, TableView<Book> tableView){

        FilteredList<Book> filteredData = new FilteredList<>(masterData, b -> true);

        Runnable updatePredicate = () -> {
            String keyword  = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
            String category = cbCategory.getValue();
            String status   = cbStatus.getValue();

            filteredData.setPredicate(book -> {
                boolean matchesKeyword = true;
                if (!keyword.trim().isEmpty()) {
                    boolean matchTitle  = book.getTitle()  != null && book.getTitle().toLowerCase().contains(keyword);
                    boolean matchAuthor = book.getAuthor() != null && book.getAuthor().toLowerCase().contains(keyword);
                    boolean matchIsbn   = book.getIsbn()   != null && book.getIsbn().replace("-", "").contains(keyword.replace("-", ""));
                    matchesKeyword = matchTitle || matchAuthor || matchIsbn;
                }

                boolean matchesCategory = category == null || category.equals("All Categories") || category.isEmpty() || (book.getCategory() != null && book.getCategory().equals(category));

                boolean matchesStatus = status == null || status.equals("All Status") || status.isEmpty() || (book.getStatus() != null && book.getStatus().equals(status));

                return matchesKeyword && matchesCategory && matchesStatus;
            });
        };

        searchField.textProperty().addListener((o, oldV, newV) -> updatePredicate.run());
        cbCategory.valueProperty().addListener((o, oldV, newV) -> updatePredicate.run());
        cbStatus.valueProperty().addListener((o, oldV, newV)   -> updatePredicate.run());

        if (btnClear != null) {
            btnClear.setOnAction(e -> {
                searchField.clear();
                cbCategory.setValue("All Categories");
                cbStatus.setValue("All Status");
            });
        }

        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public ObservableList<String> buildCategoryNames(ObservableList<Book> masterData){
        ObservableList<String> names = FXCollections.observableArrayList("All Categories");
        masterData.stream()
            .map(Book::getCategory)
            .filter(c -> c != null && !c.isBlank())
            .distinct()
            .sorted()
            .forEach(names::add);
        return names;
    }
}
