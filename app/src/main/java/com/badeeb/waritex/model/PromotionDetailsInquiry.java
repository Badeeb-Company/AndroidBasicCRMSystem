package com.badeeb.waritex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr Alghawy on 6/15/2017.
 */

public class PromotionDetailsInquiry {

    @Expose(serialize = false, deserialize = true)
    @SerializedName("promotion_info")
    private Promotion promotion;

    // Constructor
    public PromotionDetailsInquiry() {
        this.promotion = new Promotion();
    }

    // Setters and Getters

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
