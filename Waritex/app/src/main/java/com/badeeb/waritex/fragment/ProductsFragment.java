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
import com.badeeb.waritex.model.Product;
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
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    // Logging Purpose
    private final String TAG = ProductsFragment.class.getSimpleName();

    // Fragment Attributes
    private static ViewPager mViewPager;

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/products";
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMoreProducts;

    private List<Product> mProductsArray = new ArrayList<Product>();


    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {
        Log.d(TAG, "init - Start");

        // Attribute initialization
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        this.mcurrentPage = 1;
        this.mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        this.mNoMoreProducts = false;

        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(getContext(), mProductsArray);
        mViewPager.setAdapter(slideViewPagerAdapter);

        // Network call to load first 20 products
        loadProducts();

        // Add Page change listener to viewpager for handling loading of more pages
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "init - onPageSelected - Start");

                Log.d(TAG, "init - onPageSelected - Position: "+position);

                if (position == (mcurrentPage * mpageSize) - 1 && ! mNoMoreProducts) {
                    // Load more images
                    mcurrentPage++;
                    loadProducts();
                }

                Log.d(TAG, "init - onPageSelected - End");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Log.d(TAG, "init - End");
    }

    private void loadProducts() {
        Log.d(TAG, "loadProducts - Start");

        // Setting mUrl
        String currentUrl = mUrl + "?page=" + mcurrentPage + "&page_size=" + mpageSize;

        // Network call

        Log.d(TAG, "loadProducts - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadProducts - onResponse - Start");

                        Log.d(TAG, "loadProducts - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<ProductsInquiry>>() {}.getType();

                        JsonResponse<ProductsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadProducts - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadProducts - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadProducts - onResponse - Data Size: " + jsonResponse.getResult().getProducts().size());

                        // Load Data into slide show
                        if (jsonResponse.getResult().getProducts().size() != 0) {
                            mProductsArray.addAll(jsonResponse.getResult().getProducts());
                        }
                        else {
                            // All Images are loaded
                            mNoMoreProducts = true;
                        }
                        /*
                        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(getContext(), mProductsArray);
                        mViewPager.setAdapter(slideViewPagerAdapter);
                        */
                        mViewPager.getAdapter().notifyDataSetChanged();

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
