package com.example.android_3125_ex13;

public class Meal {
    private String name;
    private double price;
    private int imgSrc;

    public Meal(String name, double price, int imgSrc) {
        this.name = name;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    @Override
    public String toString() {
        return name;
    }
}
