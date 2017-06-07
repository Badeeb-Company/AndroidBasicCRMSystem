package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.badeeb.waritex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr Alghawy on 6/7/2017.
 */

public class SlideViewPagerAdapter extends PagerAdapter {

    // Class Attributes
    private List<Integer> mImages;
    private LayoutInflater mInflater;
    private Context mContext;

    public SlideViewPagerAdapter(Context context, List<Integer> images) {
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

        View productSlideLayout = this.mInflater.inflate(R.layout.slide_product, view, false);

        ImageView myImage = (ImageView) productSlideLayout.findViewById(R.id.image);
        myImage.setImageResource(this.mImages.get(position));

        view.addView(productSlideLayout, 0);

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
