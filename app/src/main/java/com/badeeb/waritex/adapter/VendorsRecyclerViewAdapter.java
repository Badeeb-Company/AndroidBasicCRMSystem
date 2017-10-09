package com.badeeb.waritex.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    // Constant
    public final static int CALL_REQUEST_PERMISSION_CODE = 3;
    private final static String TAG = VendorsRecyclerViewAdapter.class.getName();

    // Class Attributes
    private Context mContext;
    private List<Vendor> mVendorsList;
    private String mSelectedMobileNumber;
    private Fragment mParentFragment;

    // Constructor
    public VendorsRecyclerViewAdapter(Context mContext, List<Vendor> mVendorsList, Fragment mParentFragment) {
        this.mContext = mContext;
        this.mVendorsList = mVendorsList;
        this.mParentFragment = mParentFragment;
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
        Log.d(TAG, "Start - onBindViewHolder - position: " + position);
        Vendor vendor = this.mVendorsList.get(position);
        // Set vendor values in holder
        holder.getName().setText(vendor.getName());
        holder.getMobileNumber().setText(vendor.getMobileNumber());
        holder.getAddress().setText(vendor.getAddress());
        holder.getGovernorate().setText(vendor.getGovernorate());
        holder.getCallIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start - onClick Call Icon - mSelectedMobileNumber: " + mSelectedMobileNumber);
                mSelectedMobileNumber = String.valueOf(holder.getMobileNumber().getText());
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "PerformCall - call Permission not granted - mSelectedMobileNumber: " + mSelectedMobileNumber);
                    mParentFragment.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_PERMISSION_CODE);
                } else {
                    performCall();
                }
                Log.d(TAG, "End - onClick Call Icon - mSelectedMobileNumber: " + mSelectedMobileNumber);
            }
        });
    }

    public void performCall() {
        Log.d(TAG, "Start - performCall - mSelectedMobileNumber: " + mSelectedMobileNumber);

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mSelectedMobileNumber));
            mContext.startActivity(callIntent);
        }

        Log.d(TAG, "End - performCall - mSelectedMobileNumber: "+mSelectedMobileNumber);




    }

    @Override
    public int getItemCount() {
        return this.mVendorsList.size();
    }
}
