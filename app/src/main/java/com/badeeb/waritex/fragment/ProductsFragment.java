package com.badeeb.waritex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.ProductsRecyclerViewAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Product;
import com.badeeb.waritex.model.ProductsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    private RecyclerView mRecyclerView;
    private ProductsRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mProductsPerLine;

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/products";
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMoreProducts;

    private List<Product> mProductsArray;


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
        mProductsArray = new ArrayList<>();
        // swipeRefreshLayout creation
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.products_swipe_refresh_layout);
        // Recycler view creation
        mRecyclerView = (RecyclerView) view.findViewById(R.id.products_recycler_view);
        // Adapter creation
        mAdapter = new ProductsRecyclerViewAdapter(getContext(), mProductsArray);
        // Initialize number of products per line
        mProductsPerLine = AppPreferences.TWO_VIEWS_IN_LINE;      // Default Value
        // Layout Manager creation
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), mProductsPerLine);
        // Link layout manager with recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Link adapter with recycler view
        mRecyclerView.setAdapter(mAdapter);

        // Call this method to setup listener on UI components
        setupListeners();

        // Network parameters initialization
        this.mcurrentPage = 1;
        this.mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        this.mNoMoreProducts = false;


        // Network call to load first 20 products
        loadProducts();

        // applying swipe refresh on products list
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadProducts();
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
                        if (jsonResponse.getResult().getProducts() != null
                                && jsonResponse.getResult().getProducts().size() != 0) {
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
                        mAdapter.notifyDataSetChanged();

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

        // Adding retry policy to request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(AppPreferences.VOLLEY_TIME_OUT, AppPreferences.VOLLEY_RETRY_COUNTER, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyVolley.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

        // disabling the refresh icon
        mSwipeRefreshLayout.setRefreshing(false);

        Log.d(TAG, "loadProducts - End");
    }

    private void setupListeners() {

        Log.d(TAG, "setupListeners - Start");

        // Adding scroll to recycler view
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - Start");

                //check for scroll down
                if(dy > 0) {

                    if (mRecyclerView.getLayoutManager().findViewByPosition(mpageSize * mcurrentPage - 1) != null && ! mNoMoreProducts) {
                        // Scrolling started to be near to end of list
                        // Load next page

                        Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - Load more products");

                        mcurrentPage++;
                        loadProducts();
                    }
                }
                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - End");
            }
        });

        Log.d(TAG, "setupListeners - End");
    }

}
