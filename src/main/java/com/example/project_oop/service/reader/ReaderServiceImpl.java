package com.example.project_oop.service.reader;

import com.example.project_oop.models.Reader;
import com.example.project_oop.repository.ReaderRepository;
import com.example.project_oop.repository.implement.ReaderRepositoryImpl;

import java.util.List;

public class ReaderServiceImpl implements ReaderService{
    private final ReaderRepository readerRepository = new ReaderRepositoryImpl();
    @Override
    public List<Reader> getAllReaders() {
        return readerRepository.getAll();
    }
}
