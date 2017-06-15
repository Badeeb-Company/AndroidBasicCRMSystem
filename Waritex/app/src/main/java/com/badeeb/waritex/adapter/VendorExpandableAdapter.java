package com.badeeb.waritex.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Vendor;
import com.badeeb.waritex.model.VendorParent;
import com.badeeb.waritex.view.VendorDetailsViewHolder;
import com.badeeb.waritex.view.VendorParentViewHolder;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */

public class VendorExpandableAdapter extends ExpandableRecyclerAdapter<VendorParentViewHolder, VendorDetailsViewHolder> {

    private LayoutInflater minflater;

    // For logging purpose
    private final String TAG = VendorExpandableAdapter.class.getSimpleName();

    public VendorExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);

        Log.d(TAG, "VendorExpandableAdapter - Start");

        minflater = LayoutInflater.from(context);

        Log.d(TAG, "VendorExpandableAdapter - End");
    }

    @Override
    public VendorParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        Log.d(TAG, "onCreateParentViewHolder - Start");

        View view = minflater.inflate(R.layout.view_vendor_parent, viewGroup, false);

        Log.d(TAG, "onCreateParentViewHolder - End");

        return new VendorParentViewHolder(view);
    }

    @Override
    public VendorDetailsViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {

        Log.d(TAG, "onCreateChildViewHolder - Start");

        View view = minflater.inflate(R.layout.view_vendor_details, viewGroup, false);

        Log.d(TAG, "onCreateChildViewHolder - End");

        return new VendorDetailsViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(VendorParentViewHolder vendorParentViewHolder, int i, Object o) {
        Log.d(TAG, "onBindParentViewHolder - Start");

        VendorParent vendorParent = (VendorParent) o;
        vendorParentViewHolder.getVendorName().setText(vendorParent.getName());

        Log.d(TAG, "onBindParentViewHolder - End");
    }

    @Override
    public void onBindChildViewHolder(VendorDetailsViewHolder vendorDetailsViewHolder, int i, Object o) {

        Log.d(TAG, "onBindChildViewHolder - Start");

        Vendor vendor = (Vendor) o;
//        vendorDetailsViewHolder.getId().setText(vendor.getId()+"");
//        vendorDetailsViewHolder.getType().setText(vendor.getType());
        vendorDetailsViewHolder.getName().setText(vendor.getName());
        vendorDetailsViewHolder.getPhoneNumber().setText(vendor.getPhoneNumber());
        vendorDetailsViewHolder.getMobileNumber().setText(vendor.getMobileNumber());
        vendorDetailsViewHolder.getGovernorate().setText(vendor.getGovernorate());
        vendorDetailsViewHolder.getAddress().setText(vendor.getAddress());
//        vendorDetailsViewHolder.getLat().setText(vendor.getLat()+"");
//        vendorDetailsViewHolder.getLng().setText(vendor.getLng()+"");

        Log.d(TAG, "onBindChildViewHolder - End");
    }

}
