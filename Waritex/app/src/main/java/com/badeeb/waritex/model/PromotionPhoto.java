package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Amr Alghawy on 6/15/2017.
 */

@Parcel(Parcel.Serialization.BEAN)
public class PromotionPhoto implements Photo{

    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("url")
    private String url;

    // Constructor
    public PromotionPhoto() {
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

    @Override
    public int getPhotoId() {
        return this.id;
    }

    @Override
    public String getPhotoUrl() {
        return this.url;
    }
}
