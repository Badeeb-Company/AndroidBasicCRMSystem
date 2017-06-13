package com.badeeb.waritex.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.MainActivity;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.SlideViewPagerAdapter;
import com.badeeb.waritex.model.ProductsInquiry;
import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    // Logging Purpose
    private final String TAG = ProductsFragment.class.getSimpleName();

    // Fragment Attributes
    private static ViewPager mPager;

    private ArrayList<Integer> mProductsArray = new ArrayList<Integer>();

    private static final Integer[] products = {R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5};

    // attributes that will be used for JSON calls
    private final String CONTEXT_URL = "/products";
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

        if (! mProductsArray.isEmpty())
            mProductsArray.clear();

        for(int i = 0; i < products.length; i++)
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

        Gson gson = gsonBuilder.create();

        ProductsInquiry productsInquiry = new ProductsInquiry();
        productsInquiry.setPage(this.mcurrentPage);
        productsInquiry.setPageSize(this.mpageSize);

        String request = gson.toJson(productsInquiry);
        Log.d(TAG, "loadProducts - JSON Request: "+request);

        // Network call
        


        Log.d(TAG, "loadProducts - End");
    }

}
