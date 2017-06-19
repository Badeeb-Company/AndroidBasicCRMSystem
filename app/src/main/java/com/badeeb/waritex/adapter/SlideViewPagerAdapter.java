package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Photo;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Amr Alghawy on 6/7/2017.
 */

public class SlideViewPagerAdapter extends PagerAdapter {

    // Logging Purpose
    private final String TAG = SlideViewPagerAdapter.class.getSimpleName();

    // Class Attributes
    private List<? extends Photo> mImages;
    private LayoutInflater mInflater;
    private Context mContext;

    public SlideViewPagerAdapter(Context context, List<? extends Photo> images) {
        this.mImages = images;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * returns the number of available views in the ViewPager.
     */
    @Override
    public int getCount() {

        return this.mImages.size();
    }

    /**
     * This method checks whether the view passed to it is associated with the key returned by the instantiateItem().
     * This method is important for proper functioning of the PagerAdapter.
     * We just compare the two input view and the key and return the result.
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view.equals(object);
    }

    /**
     * This method should create the page for the position passed to it as an argument.
     * Here we inflate() the slide.xml layout to create the android images slider
     * set the image resource for the ImageView in it.
     * Finally, the inflated view is added to the ViewPager using addView() and return it.
     */
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        Log.d(TAG, "instantiateItem - Start");
        View productSlideLayout = this.mInflater.inflate(R.layout.slide_product, view, false);

        ImageView myImage = (ImageView) productSlideLayout.findViewById(R.id.image);

        // Load Image from Server
        Log.d(TAG, "instantiateItem - Before Glide call");
        Glide.with(mContext)
                .load(this.mImages.get(position).getPhotoUrl())
                .into(myImage);

        //myImage.setImageResource(this.mImages.get(position));
        Log.d(TAG, "instantiateItem - Position = "+position);
        Log.d(TAG, "instantiateItem - URL = "+this.mImages.get(position).getPhotoUrl());

        view.addView(productSlideLayout, 0);

        Log.d(TAG, "instantiateItem - End");

        return productSlideLayout;
    }


    /**
     * Removes the page for the given position from the container.
     * Here we have simply removed object using removeView().
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
