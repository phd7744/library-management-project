package com.example.project_oop.repository;

import com.example.project_oop.models.Reader;
import java.util.List;

public interface ReaderRepository {
    List<Reader> getAll();
    void save(Reader reader);
    void update(Reader reader);
    void delete(int id);
}