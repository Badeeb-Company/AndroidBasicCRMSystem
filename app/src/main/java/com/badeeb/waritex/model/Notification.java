package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr Alghawy on 7/5/2017.
 */

public class Notification {

    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("title")
    private String title;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("description")
    private String description;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("created_at")
    private String creationDate;

    // Constructor
    public Notification() {
        id = -1;
        title = "";
        description = "";
        creationDate = "";
    }

    // Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
