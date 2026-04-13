package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.repository.impl.BorrowDetailRepository;
import com.example.project_oop.repository.impl.BorrowReceiptRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BorrowDetailService {
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();
    private final BorrowReceiptRepository borrowReceiptRepository = new BorrowReceiptRepository();

    public List<BorrowDetail> getAllBorrowDetail(int receiptId) throws SQLException {
        return borrowDetailRepository.getByReceiptId(receiptId);
    }


    public void returnBorrow(int receiptId) throws SQLException {

        Connection conn = DatabaseConnection.getConnection();
        LocalDate today = LocalDate.now();
        try {
            conn.setAutoCommit(false);

            List<BorrowDetail> list = borrowDetailRepository.getByReceiptId(receiptId);

            for (BorrowDetail bd : list) {
                if (bd.getReturnDate() != null) continue;

                LocalDate dueDate = bd.getDueDate().toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(dueDate, today);
                if (daysLate < 0) daysLate = 0;

                double fineAmount = daysLate * 100;

                bd.setReturnDate(Date.valueOf(today));
                bd.setFineAmount(fineAmount);

                borrowDetailRepository.returnBook(bd, conn);
            }

            borrowReceiptRepository.updateStatus(receiptId, "RETURNED", conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}