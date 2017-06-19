package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr Alghawy on 6/11/2017.
 */

public class Vendor {

    // Class Attributes
    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    private int id;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("type")
    private String type;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("name")
    private String name;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("phone_number")
    private String phoneNumber;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("mobile_number")
    private String mobileNumber;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("governorate")
    private String governorate;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("address")
    private String address;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("lat")
    private double lat;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("lng")
    private double lng;

    public Vendor() {
        this.id = -1;
        this.type = "";
        this.name = "";
        this.phoneNumber = "";
        this.mobileNumber = "";
        this.governorate = "";
        this.address = "";
        this.lat = -1;
        this.lng = -1;
    }

    // Setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
