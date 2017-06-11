package com.badeeb.waritex.model;

/**
 * Created by Amr Alghawy on 6/11/2017.
 */

public class Vendor {

    // Class Attributes
    private int id;
    private String type;
    private String name;
    private String mobileNumber;
    private String governorate;
    private String address;
    private double lat;
    private double lng;

    public Vendor() {
        this.id = -1;
        this.type = "";
        this.name = "";
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

}
