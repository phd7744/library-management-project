package com.example.project_oop.models;

public class Author {
    private int id;
    private String name; 
    public Author(int id, String name)
    {
        this.id=id;
        this.name=name;
    }
    public int getid()
    {
        return id;
    }
    public String getname()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }
    public void display()
    {
        System.out.println(id + " " + name );
    }
}

