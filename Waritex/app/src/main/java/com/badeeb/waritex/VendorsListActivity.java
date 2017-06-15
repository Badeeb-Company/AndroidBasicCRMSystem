package com.badeeb.waritex;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.adapter.VendorExpandableAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Vendor;
import com.badeeb.waritex.model.VendorParent;
import com.badeeb.waritex.model.VendorsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorsListActivity extends AppCompatActivity {

    // For logging purpose
    private final String TAG = VendorsListActivity.class.getSimpleName();

    // Class Attributes
    private int mPromotionId;
    private RecyclerView mRecyclerView;
    private VendorExpandableAdapter mVendorExpandableAdapter;
    private List<ParentObject> mVendorParentList;
    private List<Vendor> mVendorsList;

    // Constants
    public final static String EXTRA_PROMOTION_ID = "EXTRA_PROMOTION_ID";

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/promotions";
    private String mContextUrl = "/vendors";
    private Location mCurrentLocation;
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMoreVendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors_list);

        init();

        Log.d(TAG, "onCreate - End");
    }

    private void init() {
        Log.d(TAG, "init - Start");

        // Attributes initialization
        // Get promotion details from previous activity
        Intent intent = getIntent();
        mPromotionId = intent.getIntExtra(EXTRA_PROMOTION_ID, -1);

        this.mVendorParentList = new ArrayList<>();
        this.mVendorsList = new ArrayList<>();

        // Recycler list adapter creation
        this.mVendorExpandableAdapter = new VendorExpandableAdapter(this, this.mVendorParentList);
        this.mVendorExpandableAdapter.setCustomParentAnimationViewId(R.id.vendors_list_item_expand_arrow);
        this.mVendorExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        this.mVendorExpandableAdapter.setParentAndIconExpandOnClick(true);

        // Recycler View creation
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        // Link between adapter and recycler list
        this.mRecyclerView.setAdapter(this.mVendorExpandableAdapter);

        // Make HTTP Call to get vendors list
        mcurrentPage = 1;
        mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        mNoMoreVendors = false;

        loadVendorsDetails();

        Log.d(TAG, "init - End");
    }

    private void loadVendorsDetails() {

        Log.d(TAG, "loadVendorsDetails - Start");

        String currentUrl = mUrl + "/" + mPromotionId + mContextUrl + "?page=" + mcurrentPage + "&page_size=" + mpageSize;

        Log.d(TAG, "loadVendorsDetails - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadVendorsDetails - onResponse - Start");

                        Log.d(TAG, "loadVendorsDetails - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<VendorsInquiry>>() {}.getType();

                        JsonResponse<VendorsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadVendorsDetails - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadVendorsDetails - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadVendorsDetails - onResponse - Photos Size: " + jsonResponse.getResult().getVendors().size());

                        // Move data to vendors list
                        mVendorsList = jsonResponse.getResult().getVendors();
                        generateVendorsList();

                        Log.d(TAG, "loadVendorsDetails - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadVendorsDetails - onErrorResponse: " + error.toString());
                    }
                }
        ) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "*");
                return headers;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);

        Log.d(TAG, "loadVendorsDetails - End");
    }
    
    
    private void generateVendorsList() {
        Log.d(TAG, "generateVendorsList - Start");

        for (Vendor vendor : this.mVendorsList) {

            ArrayList<Object> childList = new ArrayList<>();
            childList.add(vendor);

            VendorParent vendorParent = new VendorParent();
            vendorParent.setName(vendor.getName());
            vendorParent.setChildObjectList(childList);

            this.mVendorParentList.add(vendorParent);
        }

        Log.d(TAG, "mVendorParentList size: "+this.mVendorParentList.size());

        // Recycler list adapter initialization
        this.mVendorExpandableAdapter = new VendorExpandableAdapter(this, this.mVendorParentList);
        this.mVendorExpandableAdapter.setCustomParentAnimationViewId(R.id.vendors_list_item_expand_arrow);
        this.mVendorExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        this.mVendorExpandableAdapter.setParentAndIconExpandOnClick(true);

        // Recycler View initialization
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        // Link between adapter and recycler list
        this.mRecyclerView.setAdapter(this.mVendorExpandableAdapter);

        Log.d(TAG, "generateVendorsList - End");
    }

    // Create onClick event for map button
    public void onClickShowMapFloatingBttn(View view) {

        Log.d(TAG, "onClickShowMapFloatingBttn - Start");

        Intent mapIntent = new Intent(this, MapsActivity.class);
        this.startActivity(mapIntent);

        Log.d(TAG, "onClickShowMapFloatingBttn - End");
    }

}
