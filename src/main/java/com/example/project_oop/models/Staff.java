package com.example.project_oop.models;

public class Staff {
    private int id;
    private String name;
    private String position;
    public Staff(int id, String name, String position )
    {
        this.id = id;
        this.name = name;
        this.position = position;
    }
    public void display()
    {
        System.out.println(id+ " " + name + " " + position);
    }
}
