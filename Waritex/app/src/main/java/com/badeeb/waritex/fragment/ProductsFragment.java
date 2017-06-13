package com.badeeb.waritex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.SlideViewPagerAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.ProductsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    // Logging Purpose
    private final String TAG = ProductsFragment.class.getSimpleName();

    // Fragment Attributes
    private static ViewPager mPager;

    private ArrayList<Integer> mProductsArray = new ArrayList<Integer>();

    private static final Integer[] products = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5};

    // attributes that will be used for JSON calls
    private String url = AppPreferences.BASE_URL + "/products";
    private int mcurrentPage;
    private int mpageSize;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView - Start");

        // Attribute initialization
        this.mcurrentPage = 1;
        this.mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {
        Log.d(TAG, "init - Start");

        if (!mProductsArray.isEmpty())
            mProductsArray.clear();

        for (int i = 0; i < products.length; i++)
            mProductsArray.add(products[i]);

        // Network call to load first 20 products
        loadProducts();

        // Adapter creation
        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(getContext(), this.mProductsArray);

        this.mPager = (ViewPager) view.findViewById(R.id.viewpager);
        mPager.setAdapter(slideViewPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        /*
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (mCurrentPage == XMEN.length) {
                    mCurrentPage = 0;
                }
                mPager.setCurrentItem(mCurrentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //handler.post(Update);
            }
        }, 2500, 2500); // Interval: 2.5 seconds
        */
        Log.d(TAG, "init - End");
    }

    private void loadProducts() {
        Log.d(TAG, "loadProducts - Start");

        // Create Gson object
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();

        final Gson gson = gsonBuilder.create();

        url += "?page=" + mcurrentPage + "&page_size=" + mpageSize;

        // Network call

        Log.d(TAG, "loadProducts - URL: " + url);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadProducts - onResponse - Start");

                        Log.d(TAG, "loadProducts - onResponse - Json Response: " + response.toString());

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<ProductsInquiry>>() {}.getType();

                        JsonResponse<ProductsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadProducts - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadProducts - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadProducts - onResponse - Data Size: " + jsonResponse.getResult().getProducts().size());

                        Log.d(TAG, "loadProducts - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadProducts - onErrorResponse: " + error.toString());
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

        MyVolley.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        Log.d(TAG, "loadProducts - End");
    }

}
