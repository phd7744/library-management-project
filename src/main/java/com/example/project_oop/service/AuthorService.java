package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Author;
import com.example.project_oop.repository.impl.AuthorRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {

    private final AuthorRepository authorRepository = new AuthorRepository();

    public List<Author> getAllAuthors(){
        return authorRepository.getAll();
    }

    public boolean existsByName(String name) {
        return authorRepository.getAll().stream()
                .anyMatch(author -> author.getAuthorName().equals(name));
    }

    public void addAuthor(String authorName) throws SQLException {
        addAuthor(new Author(0, authorName));
    }

    public void addAuthor(Author author) throws SQLException{

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            authorRepository.create(author, conn);

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

    public void updateAuthor(Author author) throws SQLException{

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            authorRepository.update(author, conn);

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

    public void deleteAuthor(int authorId) throws SQLException{

        // Kiểm tra xem tác giả có sách hay không
        if (authorRepository.countBooksByAuthor(authorId) > 0) {
            throw new IllegalStateException("Tác giả này đang được dùng bởi một hoặc nhiều sách");
        }

        Connection conn = DatabaseConnection.getConnection();

        try{

            conn.setAutoCommit(false);

            authorRepository.delete(authorId, conn);

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
