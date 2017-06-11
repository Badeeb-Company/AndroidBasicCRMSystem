package com.badeeb.waritex.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr Alghawy on 6/11/2017.
 */

public class VendorParent implements ParentObject{

    private String name;
    private List<Object> vendor;

    public VendorParent() {
        this.name = "";
        this.vendor = new ArrayList<>();
    }

    // Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getVendor() {
        return vendor;
    }

    public void setVendor(List<Object> vendor) {
        this.vendor = vendor;
    }

    @Override
    public List<Object> getChildObjectList() {
        return vendor;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        this.vendor = list;
    }
}
