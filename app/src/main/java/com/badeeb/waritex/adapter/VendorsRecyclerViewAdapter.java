package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View itemView = layoutInflater.inflate(R.layout.venor_card, parent, false);

        VendorViewHolder vendorViewHolder = new VendorViewHolder(itemView);

        return vendorViewHolder;
    }

    @Override
    public void onBindViewHolder(VendorViewHolder holder, int position) {

        Vendor vendor = this.mVendorsList.get(position);

        // Set vendor values in holder
        holder.getMobileNumber().setText(vendor.getMobileNumber());
        holder.getAddress().setText(vendor.getAddress());
        holder.getGovernorate().setText(vendor.getGovernorate());
    }

    @Override
    public int getItemCount() {
        return this.mVendorsList.size();
    }
}
