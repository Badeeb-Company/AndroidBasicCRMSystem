package com.badeeb.waritex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.badeeb.waritex.adapter.VendorExpandableAdapter;
import com.badeeb.waritex.model.Vendor;
import com.badeeb.waritex.model.VendorParent;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

public class VendorsListActivity extends AppCompatActivity {

    // Class Attributes
    private RecyclerView recyclerView;
    private VendorExpandableAdapter vendorExpandableAdapter;
    private List<ParentObject> vendorParentList;

    private List<Vendor> vendorsList;

    // For logging purpose
    private final String TAG = VendorsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_list);

        // initialize attributes initialization
        this.vendorParentList = new ArrayList<>();
        this.vendorsList = new ArrayList<>();

        //-----------------------------------------------------------------------------------------|
        // To be deleted later
        Vendor vendor = new Vendor();
        vendor.setId(1);
        vendor.setType("Type1");
        vendor.setName("Name1");
        vendor.setAddress("Address1");
        vendor.setGovernorate("Governorate1");
        vendor.setLat(1);
        vendor.setLng(1);
        vendor.setMobileNumber("MobileNumber1");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2);
        vendor2.setType("Type2");
        vendor2.setName("Name2");
        vendor2.setAddress("Address2");
        vendor2.setGovernorate("Governorate2");
        vendor2.setLat(2);
        vendor2.setLng(2);
        vendor2.setMobileNumber("MobileNumber2");

        Vendor vendor3 = new Vendor();
        vendor3.setId(3);
        vendor3.setType("Type3");
        vendor3.setName("Name3");
        vendor3.setAddress("Address3");
        vendor3.setGovernorate("Governorate3");
        vendor3.setLat(3);
        vendor3.setLng(3);
        vendor3.setMobileNumber("MobileNumber3");

        this.vendorsList.add(vendor);
        this.vendorsList.add(vendor2);
        this.vendorsList.add(vendor3);
        //-----------------------------------------------------------------------------------------|

        // Recycler list adapter initialization
        this.vendorExpandableAdapter = new VendorExpandableAdapter(this, this.vendorParentList);
        this.vendorExpandableAdapter.setCustomParentAnimationViewId(R.id.vendors_list_item_expand_arrow);
        this.vendorExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        this.vendorExpandableAdapter.setParentAndIconExpandOnClick(true);

        // Recycler View initialization
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(mLayoutManager);

        // Link between adapter and recycler list
        this.recyclerView.setAdapter(this.vendorExpandableAdapter);

        // Make HTTP Call to get vendors list
        generateVendorsList();

        Log.d(TAG, "onCreate - End");
    }

    private void generateVendorsList() {
        Log.d(TAG, "generateVendorsList - Start");

        for (Vendor vendor : this.vendorsList) {

            ArrayList<Object> childList = new ArrayList<>();
            childList.add(vendor);

            VendorParent vendorParent = new VendorParent();
            vendorParent.setName(vendor.getName());
            vendorParent.setChildObjectList(childList);

            this.vendorParentList.add(vendorParent);
        }

        Log.d(TAG, "vendorParentList size: "+this.vendorParentList.size());

        // Recycler list adapter initialization
        this.vendorExpandableAdapter = new VendorExpandableAdapter(this, this.vendorParentList);
        this.vendorExpandableAdapter.setCustomParentAnimationViewId(R.id.vendors_list_item_expand_arrow);
        this.vendorExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        this.vendorExpandableAdapter.setParentAndIconExpandOnClick(true);

        // Recycler View initialization
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(mLayoutManager);

        // Link between adapter and recycler list
        this.recyclerView.setAdapter(this.vendorExpandableAdapter);

        Log.d(TAG, "generateVendorsList - End");
    }

    // Create onClick event for map button
    public void onClickShowMapFloatingBttn(View view) {

        Log.d(TAG, "onClickShowMapFloatingBttn - Start");

        

        Log.d(TAG, "onClickShowMapFloatingBttn - End");
    }

}
