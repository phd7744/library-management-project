package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Book;
import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.BorrowReceipt;
import com.example.project_oop.repository.impl.BorrowDetailRepository;
import com.example.project_oop.repository.impl.BorrowReceiptRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

public class BorrowReceiptService {
    private final BorrowReceiptRepository borrowReceiptRepository = new BorrowReceiptRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    public List<BorrowReceipt> getAllReceipt() {
        return borrowReceiptRepository.get();
    }

    public void issueBook(BorrowReceipt receipt, List<Book> books, Date dueDate) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int receiptId = borrowReceiptRepository.create(receipt, conn);
            if (receiptId <= 0) {
                throw new SQLException("Không tạo được phiếu mượn");
            }

            for (Book book : books) {
                BorrowDetail detail = new BorrowDetail();
                detail.setReceiptId(receiptId);
                detail.setBookId(book.getId());
                detail.setDueDate(dueDate);
                borrowDetailRepository.create(detail, conn);
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
