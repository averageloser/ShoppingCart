package com.averageloser.shoppingcart.model;

/**
 * Created by tj on 10/2/2015.
 */
public class Item {
    private String name;
    private String description;
    private double price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return getName() + " " + getPrice();
    }
}
