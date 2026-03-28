package com.example.project_oop.models;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String category;
    private String author;
    private String publisher;
    private int publishYear;
    private int quantity;
    private String status;

    public Book(int id, String isbn, String title, String category, String author, String publisher, int publishYear, int quantity, String status) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getPublisher(){
        return publisher;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public int getPublishYear(){
        return publishYear;
    }

    public void setPublishYear(int publishYear){
        this.publishYear = publishYear;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
