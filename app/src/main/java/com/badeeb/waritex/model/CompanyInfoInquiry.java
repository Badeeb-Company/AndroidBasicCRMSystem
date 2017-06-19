package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr Alghawy on 6/16/2017.
 */

public class CompanyInfoInquiry {

    // Class Attributes
    @Expose(serialize = false, deserialize = true)
    @SerializedName("company_info")
    private CompanyInfo companyInfo;

    public CompanyInfoInquiry() {
        this.companyInfo = new CompanyInfo();
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }
}
