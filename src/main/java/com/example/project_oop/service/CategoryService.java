package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.Category;
import com.example.project_oop.repository.impl.CategoryRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryService{

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public List<Category> getAllCategories(){
        return categoryRepository.getAll();
    }

    public void addCategory(String name) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            categoryRepository.add(name, conn);
            conn.commit();
        }
        catch(Exception e){
            if (conn != null) conn.rollback();
            throw new RuntimeException(e);
        }
        finally{
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void updateCategory(int id, String newName) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            categoryRepository.update(id, newName, conn);
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

    public void deleteCategory(int id) throws SQLException{
        Connection conn = DatabaseConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            boolean deleted = categoryRepository.delete(id, conn);
            if(!deleted){
                conn.rollback();
                throw new IllegalStateException("in-use");
            }
            conn.commit();
        }
        catch(IllegalStateException e){
            throw e;
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

    public boolean existsByName(String name){
        return categoryRepository.existsByName(name);
    }
}
