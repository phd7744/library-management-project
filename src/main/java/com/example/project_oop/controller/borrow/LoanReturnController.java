package com.example.project_oop.controller.borrow;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.Reader;
import com.example.project_oop.models.view.BorrowDetailView;
import com.example.project_oop.models.view.BorrowReceiptView;
import com.example.project_oop.models.Book;
import com.example.project_oop.service.BorrowDetailService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class LoanReturnController {

    @FXML
    private TableView<BorrowReceiptView> activeLoansTable;

    @FXML
    private Button btnAddBook;

    @FXML
    private Button btnConfirmReturn;

    @FXML
    private Button btnIssueBook;

    @FXML
    private Button btnSearchReceipt;

    @FXML
    private TableColumn<BorrowReceiptView, Date> colBorrowDate;

    @FXML
    private TableColumn<BorrowReceiptView, Date> colDueDate;

    @FXML
    private TableColumn<BorrowReceiptView, String> colReaderName;

    @FXML
    private TableColumn<BorrowReceiptView, String> colStatus;

    @FXML
    private TableColumn<BorrowReceiptView, Integer> colTotalBooks;


    @FXML
    private TableColumn<BorrowReceiptView, Integer> colReceiptId;

    @FXML
    private TableColumn<BorrowDetailView, Integer> colReturnBookId;

    @FXML
    private TableColumn<BorrowDetailView, String> colReturnBookTitle;

    @FXML
    private TableColumn<BorrowDetailView, Date> colReturnDueDate;

    @FXML
    private TableColumn<BorrowDetailView, Double> colReturnFine;


    @FXML
    private TableColumn<Book, Integer> colSelectedBookId;

    @FXML
    private TableColumn<Book, String> colSelectedBookTitle;

    @FXML
    private DatePicker dpDueDate;

    @FXML
    private TableView<BorrowDetailView> tblBorrowedBooks;

    @FXML
    private TableView<Book> tblSelectedBooks;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtReaderId;

    @FXML
    private TextField txtReceiptId;

    @FXML
    private TextField txtSearchLoan;

    private final BorrowDetailService borrowDetailService = new BorrowDetailService();


    @FXML
    public void initialize(){

        // 1. Danh sách book mượn
        colSelectedBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSelectedBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        // 2. Table: Sách được trả
        colReturnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colReturnBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colReturnDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnFine.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));

        // 3. Table: Các Phiếu Mượn Hiện Tại
        colReceiptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        colReaderName.setCellValueFactory(new PropertyValueFactory<>("readerName"));
        colTotalBooks.setCellValueFactory(new PropertyValueFactory<>("totalBooks"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @FXML
    void handleAddBookToList(ActionEvent event) {

    }

    @FXML
    void handleConfirmReturn(ActionEvent event) {

    }

    @FXML
    void handleIssueBook(ActionEvent event) {

    }

    @FXML
    void handleSearchReceipt(ActionEvent event) {
        int receiptId = Integer.parseInt(txtReceiptId.getText());
        try {
            List<BorrowDetailView> list = borrowDetailService.getAllBorrowDetail(receiptId);
            ObservableList<BorrowDetailView> borrowDetailList = FXCollections.observableArrayList(list);
            tblBorrowedBooks.setItems(borrowDetailList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
