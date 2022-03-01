package com.example.carassist;

public class Car {
    private String name;
    private int price;
    private String agency;
    private String comments;
    private String photoPath;

    public Car(String name, int price, String agency, String comments, String photoPath)
    {
        this.name = name;
        this.price = price;
        this.agency = agency;
        this.comments = comments;
        this.photoPath = photoPath;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }

    public String getAgency()
    {
        return agency;
    }

    public String getPhotoPath()
    {
        return photoPath;
    }

    public String getComments()
    {
        return comments;
    }
}
