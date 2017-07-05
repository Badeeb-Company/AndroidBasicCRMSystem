package com.badeeb.waritex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.SlideViewPagerAdapter;
import com.badeeb.waritex.model.Product;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    // For logging purpose
    public static final String TAG = ProductDetailsFragment.class.getName();

    private Product mProduct;

    // Constants
    public final static String EXTRA_PRODUCT_OBJECT = "EXTRA_PRODUCT_OBJECT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {

        Log.d(TAG, "init - Start");

        // Get promotion details from previous activity
        mProduct = Parcels.unwrap(getArguments().getParcelable(EXTRA_PRODUCT_OBJECT));

        updateProductDetailsOnView(view);

        Log.d(TAG, "init - End");

    }

    private void updateProductDetailsOnView(View view) {

        Log.d(TAG, "updateProductDetailsOnView - Start");

        // Find view elements and assign values to them
        // Product title
        TextView productName = (TextView) view.findViewById(R.id.product_details_Name);
        productName.setText(mProduct.getName());

        // Product description
        TextView productDescription = (TextView) view.findViewById(R.id.product_details_description);
        productDescription.setText(mProduct.getDescription());

        // Product Photo
        ImageView productImage = (ImageView) view.findViewById(R.id.product_details_image);
        Glide.with(this).load(mProduct.getPhotoUrl()).into(productImage);

        Log.d(TAG, "updateProductDetailsOnView - End");

    }

}
