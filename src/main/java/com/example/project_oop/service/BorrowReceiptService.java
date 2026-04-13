package com.example.project_oop.service;

import com.example.project_oop.models.BorrowReceipt;
import com.example.project_oop.repository.impl.BorrowDetailRepository;
import com.example.project_oop.repository.impl.BorrowReceiptRepository;

import java.util.List;

public class BorrowReceiptService {
    private final BorrowReceiptRepository borrowReceiptRepository = new BorrowReceiptRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    public List<BorrowReceipt> getAllReceipt(){
        return borrowReceiptRepository.get();
    }

}
