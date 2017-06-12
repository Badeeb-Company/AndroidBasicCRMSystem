package com.badeeb.waritex.model;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */

public class Product {

    // Class Attributes
    private int id;
    private String url;

    // Constructor
    public Product() {
        this.id = -1;
        this.url = "";
    }

    // Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
