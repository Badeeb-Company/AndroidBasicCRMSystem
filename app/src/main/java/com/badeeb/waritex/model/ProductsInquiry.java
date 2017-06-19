package com.badeeb.waritex.model;

import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */

public class ProductsInquiry {

    // Class Attributes
    @Expose(serialize = true, deserialize = false)
    @SerializedName("page")
    private int page;

    @Expose(serialize = true, deserialize = false)
    @SerializedName("page_size")
    private int pageSize;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("products")
    private List<Product> products;

    // Constructor
    public ProductsInquiry() {
        this.page = 1;      // Default values
        this.pageSize = AppPreferences.DEFAULT_PAGE_SIZE; // Default values
        this.products = new ArrayList<>();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
