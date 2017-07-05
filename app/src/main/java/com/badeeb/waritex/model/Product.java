package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Product implements Photo {

    // Class Attributes
    @Expose
    private int id;
    @Expose
    private String url;
    @Expose
    private String name;
    @Expose
    private String description;

    // Constructor
    public Product() {
        this.id = -1;
        this.url = "";
        this.name = "";
        this.description = "";
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

    // Interface method implementation
    @Override
    public int getPhotoId() {
        return this.id;
    }

    @Override
    public String getPhotoUrl() {
        return this.url;
    }
}
