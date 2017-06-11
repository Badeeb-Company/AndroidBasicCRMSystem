package com.badeeb.waritex.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.badeeb.waritex.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;


/**
 * Created by Amr Alghawy on 6/11/2017.
 */

public class VendorParentViewHolder extends ParentViewHolder {

    // Class Attributes
    private TextView vendorName;
    private ImageButton dropDownArrow;

    // Constructor
    public VendorParentViewHolder(View itemView) {
        super(itemView);

        this.vendorName = (TextView) itemView.findViewById(R.id.vendors_list_item_vendor_name);
        this.dropDownArrow = (ImageButton) itemView.findViewById(R.id.vendors_list_item_expand_arrow);
    }

    // Setters and Getters
    public TextView getVendorName() {
        return vendorName;
    }

    public void setVendorName(TextView vendorName) {
        this.vendorName = vendorName;
    }

    public ImageButton getDropDownArrow() {
        return dropDownArrow;
    }

    public void setDropDownArrow(ImageButton dropDownArrow) {
        this.dropDownArrow = dropDownArrow;
    }
}
