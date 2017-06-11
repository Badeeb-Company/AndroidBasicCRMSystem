package com.badeeb.waritex.view;

import android.view.View;
import android.widget.TextView;

import com.badeeb.waritex.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;


/**
 * Created by Amr Alghawy on 6/11/2017.
 */

public class VendorDetailsViewHolder extends ChildViewHolder {

    // Class Attributes
    private TextView id;
    private TextView type;
    private TextView name;
    private TextView mobileNumber;
    private TextView governorate;
    private TextView address;
    private TextView lat;
    private TextView lng;

    // Constructor
    public VendorDetailsViewHolder(View itemView) {
        super(itemView);

        this.id = (TextView) itemView.findViewById(R.id.vendor_details_item_id);
        this.type = (TextView) itemView.findViewById(R.id.vendor_details_item_type);
        this.name = (TextView) itemView.findViewById(R.id.vendor_details_item_name);
        this.mobileNumber = (TextView) itemView.findViewById(R.id.vendor_details_item_mobile_number);
        this.governorate = (TextView) itemView.findViewById(R.id.vendor_details_item_governorate);
        this.address = (TextView) itemView.findViewById(R.id.vendor_details_item_address);
        this.lat = (TextView) itemView.findViewById(R.id.vendor_details_item_lat);
        this.lng = (TextView) itemView.findViewById(R.id.vendor_details_item_Lng);
    }

    // Setters  and Getters
    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(TextView mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public TextView getGovernorate() {
        return governorate;
    }

    public void setGovernorate(TextView governorate) {
        this.governorate = governorate;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getLat() {
        return lat;
    }

    public void setLat(TextView lat) {
        this.lat = lat;
    }

    public TextView getLng() {
        return lng;
    }

    public void setLng(TextView lng) {
        this.lng = lng;
    }
}
