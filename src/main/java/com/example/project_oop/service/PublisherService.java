package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Publisher;
import com.example.project_oop.repository.impl.PublisherRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PublisherService {

    private final PublisherRepository publisherRepository = new PublisherRepository();

    public List<Publisher> getAllPublishers(){
        return publisherRepository.getAll();
    }

    public boolean existsByName(String name){
        return publisherRepository.getAll().stream()
                .anyMatch(publisher -> publisher.getPublisherName().equals(name));
    }

    public void addPublisher(String publisherName, String publisherAddress) throws SQLException {
        addPublisher(new Publisher(0, publisherName, publisherAddress));
    }

    public void addPublisher(Publisher publisher) throws SQLException{

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            publisherRepository.create(publisher, conn);

            conn.commit();

        }
        catch(SQLException | RuntimeException e){

            if(conn != null)
                conn.rollback();

            throw new RuntimeException(e);

        }
        finally{

            if(conn != null){

                conn.setAutoCommit(true);
                conn.close();

            }

        }
    }

    public void updatePublisher(Publisher publisher) throws SQLException{

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            publisherRepository.update(publisher, conn);

            conn.commit();

        }
        catch(SQLException | RuntimeException e){

            if(conn != null)
                conn.rollback();

            throw new RuntimeException(e);

        }
        finally{

            if(conn != null){

                conn.setAutoCommit(true);
                conn.close();

            }

        }
    }

    public void deletePublisher(int publisherId) throws SQLException{

        // Kiểm tra xem NXB có sách hay không
        if (publisherRepository.countBooksByPublisher(publisherId) > 0) {
            throw new IllegalStateException("Nhà xuất bản này đang được dùng bởi một hoặc nhiều sách");
        }

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            publisherRepository.delete(publisherId, conn);

            conn.commit();

        }
        catch(SQLException | RuntimeException e){

            if(conn != null)
                conn.rollback();

            throw new RuntimeException(e);

        }
        finally{

            if(conn != null){

                conn.setAutoCommit(true);
                conn.close();

            }

        }
    }

}

