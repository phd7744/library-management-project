package com.example.project_oop.models;

public class Publisher {
    private int id;
    private String name;
    private String address;
    public Publisher(int id, String name, String address)
    {
        this.id=id;
        this.name=name;
        this.address=address;
    }
    public int getid()
    {
        return id;
    }
    public String getname()
    {
        return name;
    }
    public String getaddress()
    {
        return address;
    }
    public void setName()
    {
        this.name= name;
    }
    public void setAddress()
    {
        this.address= address;
    }
    public void display()
    {
        System.out.println(id + " "+ name + " " +address);
    }
}
