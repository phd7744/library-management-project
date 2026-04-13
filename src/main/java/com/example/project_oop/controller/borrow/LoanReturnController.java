package com.example.project_oop.controller.borrow;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.BorrowReceipt;
import com.example.project_oop.models.view.BorrowDetailView;
import com.example.project_oop.models.view.BorrowReceiptView;
import com.example.project_oop.models.Book;
import com.example.project_oop.service.BorrowDetailService;
import com.example.project_oop.service.BorrowReceiptService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private TableView<BorrowDetail> tblBorrowedBooks;

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

    @FXML
    private Label lblTotalFine;

    private final BorrowDetailService borrowDetailService = new BorrowDetailService();
    private final BorrowReceiptService borrowReceiptService = new BorrowReceiptService();

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
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        loadData();
    }

    @SuppressWarnings("unchecked")
    void loadData(){
        List<BorrowReceipt> brList = borrowReceiptService.getAllReceipt();
        // Repository trả về List<BorrowReceiptView> (subclass), cast an toàn
        ObservableList<BorrowReceiptView> items = FXCollections.observableArrayList(
                (List<BorrowReceiptView>)(List<?>) brList
        );
        activeLoansTable.setItems(items);
    }

    @FXML
    void handleAddBookToList(ActionEvent event) {

    }

    @FXML
    void handleConfirmReturn(ActionEvent event) {
        int receiptId = Integer.parseInt(txtReceiptId.getText());
        try {
            borrowDetailService.returnBorrow(receiptId);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void handleIssueBook(ActionEvent event) {

    }

    @FXML
    void handleSearchReceipt(ActionEvent event) {
        int receiptId = Integer.parseInt(txtReceiptId.getText());
        try {
            List<BorrowDetail> list = borrowDetailService.getAllBorrowDetail(receiptId);
            LocalDate today = LocalDate.now();
            double total = 0;

            for (BorrowDetail bd : list) {
                double fine = 0;
                if ( (bd.getReturnDate() == null)){
                    LocalDate dueDate = bd.getDueDate().toLocalDate();

                    long daysLate = ChronoUnit.DAYS.between(dueDate, today);

                    fine = Math.max(daysLate, 0) * 100;


                } else {
                    fine = bd.getFineAmount();
                }

                bd.setFineAmount(fine);
                total += fine;
            }

            ObservableList<BorrowDetail> borrowDetailList = FXCollections.observableArrayList(list);
            tblBorrowedBooks.setItems(borrowDetailList);
            lblTotalFine.setText(String.format("%.0f", total));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}