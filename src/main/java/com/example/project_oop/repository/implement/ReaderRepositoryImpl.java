package com.example.project_oop.repository.implement;

import com.example.project_oop.models.Reader;
import com.example.project_oop.repository.ReaderRepository;
import com.example.project_oop.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReaderRepositoryImpl implements ReaderRepository {
    @Override
    public List<Reader> getAll() {
        List<Reader> readerList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM readers";
        try
        {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Reader r = new Reader(
                        rs.getInt("reader_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("reader_type"),
                        rs.getDouble("debt")
                );
                readerList.add(r);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return readerList;
    }

    @Override
    public void save(Reader reader) {

    }

    @Override
    public void update(Reader reader) {

    }

    @Override
    public void delete(int id) {

    }
}
