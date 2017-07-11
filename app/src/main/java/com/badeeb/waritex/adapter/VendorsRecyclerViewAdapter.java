package com.badeeb.waritex.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Vendor;
import com.badeeb.waritex.view.VendorViewHolder;

import java.util.List;

/**
 * Created by Amr Alghawy on 6/18/2017.
 */

public class VendorsRecyclerViewAdapter extends RecyclerView.Adapter<VendorViewHolder> {

    // Class Attributes
    private Context mContext;
    private List<Vendor> mVendorsList;

    // Constructor
    public VendorsRecyclerViewAdapter(Context mContext, List<Vendor> mVendorsList) {
        this.mContext = mContext;
        this.mVendorsList = mVendorsList;
    }

    @Override
    public VendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.card_venor, parent, false);

        VendorViewHolder vendorViewHolder = new VendorViewHolder(itemView);

        return vendorViewHolder;
    }

    @Override
    public void onBindViewHolder(final VendorViewHolder holder, int position) {

        Vendor vendor = this.mVendorsList.get(position);

        // Set vendor values in holder
        holder.getName().setText(vendor.getName());
        holder.getMobileNumber().setText(vendor.getMobileNumber());
        holder.getAddress().setText(vendor.getAddress());
        holder.getGovernorate().setText(vendor.getGovernorate());
        holder.getCallIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String mobileNumber = String.valueOf(holder.getMobileNumber().getText());
                callIntent.setData(Uri.parse("tel:" + mobileNumber));

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Show Alert to enable location service
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.enable_phone_call), Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 0);
                }else {
                    mContext.startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mVendorsList.size();
    }
}
