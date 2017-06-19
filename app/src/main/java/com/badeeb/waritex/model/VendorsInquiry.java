package com.badeeb.waritex.model;

import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr Alghawy on 6/16/2017.
 */

public class VendorsInquiry {

    // Class Attributes
    @Expose(serialize = true, deserialize = false)
    @SerializedName("page")
    private int page;

    @Expose(serialize = true, deserialize = false)
    @SerializedName("page_size")
    private int pageSize;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("vendors")
    private List<Vendor> vendors;

    // Default Constructor
    public VendorsInquiry() {
        this.page = -1;
        this.pageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        this.vendors = new ArrayList<>();
    }

    // Setters and Getters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}
