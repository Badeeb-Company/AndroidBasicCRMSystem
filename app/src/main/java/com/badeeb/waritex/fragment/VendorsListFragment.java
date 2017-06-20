package com.badeeb.waritex.fragment;


import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.VendorsRecyclerViewAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Vendor;
import com.badeeb.waritex.model.VendorsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorsListFragment extends Fragment {

    // Logging Purpose
    public static final String TAG = VendorsListFragment.class.getSimpleName();

    // Class Attributes
    private int mPromotionId;
    private RecyclerView mRecyclerView;
    private VendorsRecyclerViewAdapter mAdapter;
    private List<Vendor> mVendorsList;
    private int mVendorsPerLine;

    // Constants
    public final static String EXTRA_PROMOTION_ID = "EXTRA_PROMOTION_ID";

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/promotions";
    private String mContextUrl = "/vendors";
    private Location mCurrentLocation;
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMoreVendors;


    public VendorsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vendors_list, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {

        Log.d(TAG, "init - Start");

        // Get promotion details from previous activity
        mPromotionId = Parcels.unwrap(getArguments().getParcelable(EXTRA_PROMOTION_ID));

        // Attribute initialization
        mVendorsList = new ArrayList<>();
        mVendorsPerLine = AppPreferences.ONE_VIEW_IN_LINE;

        // Recycler View creation
        mRecyclerView = (RecyclerView) view.findViewById(R.id.vendors_recycler_view);
        // Adapter creation
        mAdapter = new VendorsRecyclerViewAdapter(getActivity(), mVendorsList);
        // Layout Manager creation
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // Link layout manager with recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Link adapter with recycler view
        mRecyclerView.setAdapter(mAdapter);

        // Adding scroll to recycler view
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d(TAG, "init - onScrolled - Start");

                //check for scroll down
                if(dy > 0) {

                    if (mRecyclerView.getLayoutManager().findViewByPosition(mpageSize * mcurrentPage - 1) != null && ! mNoMoreVendors) {
                        // Scrolling started to be near to end of list
                        // Load next page

                        Log.d(TAG, "init - onScrolled - Load more vendors");

                        mcurrentPage++;
                        loadVendorsDetails();
                    }
                }
                Log.d(TAG, "init - onScrolled - End");
            }
        });

        // Add onclick listener for map button
        Button mapButton = (Button) view.findViewById(R.id.map_bttn);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "init - onClick - Start");

                // Fragment creation
                MapFragment mapFragment = new MapFragment();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.add(R.id.main_frame, mapFragment, mapFragment.TAG);
//                        fragmentTransaction.replace(R.id.main_frame, promotionDetailsFragment);

                fragmentTransaction.addToBackStack(TAG);

                fragmentTransaction.commit();

                Log.d(TAG, "init - onClick - End");
            }
        });

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
                        if (jsonResponse.getResult().getVendors() != null
                                && jsonResponse.getResult().getVendors().size() != 0) {
                            mVendorsList.addAll(jsonResponse.getResult().getVendors());
                        }
                        else {
                            mNoMoreVendors = true;
                        }

                        mAdapter.notifyDataSetChanged();

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

        // Adding retry policy to request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(AppPreferences.VOLLEY_TIME_OUT, AppPreferences.VOLLEY_RETRY_COUNTER, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyVolley.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

        Log.d(TAG, "loadVendorsDetails - End");
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
