package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Book;
import com.example.project_oop.repository.impl.BookRepository;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookService{

    private final BookRepository bookRepository = new BookRepository();

    public List<Book> getAllBooks(){
        return bookRepository.getAll();
    }

    public void addBook(Book book) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            bookRepository.add(book, conn);
            conn.commit();
        }
        catch(Exception e){
            if(conn != null) conn.rollback();
            throw new RuntimeException(e);
        }
        finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void updateBook(Book book) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            bookRepository.update(book, conn);
            conn.commit();
        }
        catch(Exception e){
            if(conn != null) conn.rollback();
            throw new RuntimeException(e);
        }
        finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void deleteBook(int bookId) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            bookRepository.delete(bookId, conn);
            conn.commit();
        }
        catch(Exception e){
            if(conn != null) conn.rollback();
            throw new RuntimeException(e);
        }
        finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    public Book getBookById(int bookId) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        Book book = null;
        try{
            conn.setAutoCommit(false);
            book = bookRepository.getById(bookId, conn);
            conn.commit();
        }
        catch(Exception e){
            if(conn != null) conn.rollback();
            throw new RuntimeException(e);
        }
        finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return book;
    }


}
