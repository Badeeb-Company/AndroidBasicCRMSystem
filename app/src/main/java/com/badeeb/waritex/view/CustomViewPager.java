package com.badeeb.waritex.view;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by ahmed on 7/10/2017.
 */

public class CustomViewPager extends ViewPager {

    FragmentPagerAdapter mFragmentPagerAdapter;

    public CustomViewPager(Context context){
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mFragmentPagerAdapter != null) {
            super.setAdapter(mFragmentPagerAdapter);
//            .setViewPager(this);
        }
    }

    public void setmFragmentPagerAdapter(FragmentPagerAdapter mFragmentPagerAdapter) {
        this.mFragmentPagerAdapter = mFragmentPagerAdapter;
    }

    public void storeAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        mFragmentPagerAdapter = fragmentPagerAdapter;
    }



}
