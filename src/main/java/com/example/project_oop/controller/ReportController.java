package com.example.project_oop.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableView;

public class ReportController {

    @FXML
    private TableView<?> reportTableView;

    @FXML
    private BarChart<?, ?> reportChart;

    @FXML
    public void initialize() {
        System.out.println("Giao diện Báo cáo Thống kê đã sẵn sàng!");
    }
}