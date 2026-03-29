package com.example.project_oop.service;

import com.example.project_oop.models.BorrowDetail;
import com.example.project_oop.models.view.BorrowDetailView;
import com.example.project_oop.repository.impl.BorrowDetailRepository;

import java.sql.SQLException;
import java.util.List;

public class BorrowDetailService {
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    public List<BorrowDetailView> getAllBorrowDetail(int receiptId) throws SQLException {

        return borrowDetailRepository.getByReceiptId(receiptId);
    }


    public BorrowDetail addBorrow() throws SQLException {
        return null;
    }
}
