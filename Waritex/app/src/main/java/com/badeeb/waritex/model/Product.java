package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */

public class Product {

    // Class Attributes
    @Expose
    private int id;
    @Expose
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
