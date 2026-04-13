package com.example.project_oop.controller;

import com.example.project_oop.models.view.RecentTransactionDTO;
import com.example.project_oop.service.DashboardService;
import com.example.project_oop.service.LoginRole;
import com.example.project_oop.service.LoginSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DashBoardController {

    // --- Stat card labels ---
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label totalBooksLabel;
    @FXML
    private Label totalReadersLabel;
    @FXML
    private Label booksOnLoanLabel;
    @FXML
    private Label overdueLabel;

    // --- Recent Transactions table ---
    @FXML
    private TableView<RecentTransactionDTO> recentActivityTable;
    @FXML
    private TableColumn<RecentTransactionDTO, Integer> receiptIdCol;
    @FXML
    private TableColumn<RecentTransactionDTO, String> readerCol;
    @FXML
    private TableColumn<RecentTransactionDTO, String> bookTitleCol;
    @FXML
    private TableColumn<RecentTransactionDTO, Date> dateCol;
    @FXML
    private TableColumn<RecentTransactionDTO, String> statusCol;

    // --- PieChart ---
    @FXML
    private PieChart bookStatusChart;

    // --- Action Required labels ---
    @FXML
    private Label overdueWarningLabel;
    @FXML
    private Label unpaidFinesLabel;

    private final DashboardService dashboardService = new DashboardService();
    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

    @FXML
    public void initialize() {
        // Welcome label
        LoginRole role = LoginSession.getCurrentRole() != null ? LoginSession.getCurrentRole() : LoginRole.ADMIN;
        String fullName = LoginSession.getCurrentFullName();
        String username = LoginSession.getCurrentUsername();
        String displayName = (fullName != null && !fullName.isBlank()) ? fullName
                : (username != null ? username : role.getDisplayName());
        welcomeLabel.setText("Welcome back, " + displayName + "!");

        // Load statistics
        loadStatCards();
        loadRecentTransactions();
        loadPieChart();
        loadActionRequired();
    }

    private void loadStatCards() {
        int totalBooks = dashboardService.getTotalBooks();
        int totalReaders = dashboardService.getTotalReaders();
        int booksOnLoan = dashboardService.getBooksOnLoan();
        int overdueItems = dashboardService.getOverdueItems();

        totalBooksLabel.setText(numberFormat.format(totalBooks));
        totalReadersLabel.setText(numberFormat.format(totalReaders));
        booksOnLoanLabel.setText(numberFormat.format(booksOnLoan));
        overdueLabel.setText(numberFormat.format(overdueItems));
    }

    private void loadRecentTransactions() {
        // Setup columns
        receiptIdCol.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        readerCol.setCellValueFactory(new PropertyValueFactory<>("readerName"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Format date column
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(sdf.format(item));
                }
            }
        });

        // Format status column with color badges
        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(switch (item) {
                        case "BORROWING" ->
                                "-fx-text-fill: #f59e0b; -fx-font-weight: bold;";
                        case "RETURNED" ->
                                "-fx-text-fill: #10b981; -fx-font-weight: bold;";
                        case "OVERDUE" ->
                                "-fx-text-fill: #ef4444; -fx-font-weight: bold;";
                        default -> "-fx-font-weight: bold;";
                    });
                }
            }
        });

        // Load data
        List<RecentTransactionDTO> transactions = dashboardService.getRecentTransactions();
        ObservableList<RecentTransactionDTO> data = FXCollections.observableArrayList(transactions);
        recentActivityTable.setItems(data);

        // Placeholder khi không có data
        recentActivityTable.setPlaceholder(new Label("No recent transactions"));
    }

    private void loadPieChart() {
        Map<String, Integer> counts = dashboardService.getBookStatusCounts();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 0) {
                pieData.add(new PieChart.Data(entry.getKey() + " (" + numberFormat.format(entry.getValue()) + ")",
                        entry.getValue()));
            }
        }

        // Nếu không có data, hiển thị placeholder
        if (pieData.isEmpty()) {
            pieData.add(new PieChart.Data("No data", 1));
        }

        bookStatusChart.setData(pieData);
        bookStatusChart.setTitle(null);

        // Apply colors after chart is rendered
        bookStatusChart.applyCss();
        bookStatusChart.layout();
        applyPieChartColors();
    }

    private void applyPieChartColors() {
        String[] colors = {"#10b981", "#4a90e2", "#ef4444"};  // Available, On Loan, Overdue
        ObservableList<PieChart.Data> data = bookStatusChart.getData();
        for (int i = 0; i < data.size() && i < colors.length; i++) {
            data.get(i).getNode().setStyle("-fx-pie-color: " + colors[i] + ";");
        }
    }

    private void loadActionRequired() {
        int overdueThisWeek = dashboardService.getOverdueThisWeek();
        double totalFines = dashboardService.getTotalUnpaidFines();

        if (overdueThisWeek > 0) {
            overdueWarningLabel.setText("• " + overdueThisWeek + " books are overdue this week.");
        } else {
            overdueWarningLabel.setText("• No overdue books this week. ✅");
            overdueWarningLabel.setTextFill(javafx.scene.paint.Color.web("#059669"));
        }

        if (totalFines > 0) {
            NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.US);
            unpaidFinesLabel.setText("• Total fines: " + currencyFormat.format(totalFines) + " VND");
        } else {
            unpaidFinesLabel.setText("• No outstanding fines. ✅");
            unpaidFinesLabel.setTextFill(javafx.scene.paint.Color.web("#059669"));
        }
    }
}
