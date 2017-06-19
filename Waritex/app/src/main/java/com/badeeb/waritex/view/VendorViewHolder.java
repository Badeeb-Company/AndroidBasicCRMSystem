package com.badeeb.waritex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.badeeb.waritex.R;

import org.w3c.dom.Text;

/**
 * Created by Amr Alghawy on 6/18/2017.
 */

public class VendorViewHolder extends RecyclerView.ViewHolder{

    private TextView mobileNumber;
    private TextView address;
    private TextView governorate;

    public VendorViewHolder(View itemView) {
        super(itemView);

        this.mobileNumber = (TextView) itemView.findViewById(R.id.mobile_number);
        this.address = (TextView) itemView.findViewById(R.id.address);
        this.governorate = (TextView) itemView.findViewById(R.id.governorate);
    }

    public TextView getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(TextView mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getGovernorate() {
        return governorate;
    }

    public void setGovernorate(TextView governorate) {
        this.governorate = governorate;
    }
}
