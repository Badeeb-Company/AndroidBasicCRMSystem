package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;


/**
 * Created by Amr Alghawy on 6/16/2017.
 */

public class CompanyInfo implements Serializable{

    // Class Attributes
    @Expose(serialize = false, deserialize = true)
    @SerializedName("about")
    private String about;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("telephone")
    private String telephone;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("fax")
    private String fax;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("website")
    private String website;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("facebook_page")
    private String facebookPage;

    @Expose(serialize = false, deserialize = true)
    @SerializedName("whatsapp_number")
    private String whatsappNumber;

    // Default Constructor
    public CompanyInfo() {
        this.about = "";
        this.telephone = "";
        this.fax = "";
        this.website = "";
        this.facebookPage = "";
        this.whatsappNumber = "";
    }

    // Setters and Getters
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }
}
