package com.badeeb.waritex.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.MainActivity;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.SlideViewPagerAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import android.os.Bundle;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    // Fragment Attributes
    private static ViewPager mPager;
    private static int mCurrentPage = 0;
    private ArrayList<Integer> mXMENArray = new ArrayList<Integer>();

    private static final Integer[] XMEN= {R.drawable.beast,R.drawable.charles,R.drawable.magneto,R.drawable.mystique,R.drawable.wolverine};



    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        for(int i=0;i<XMEN.length;i++)
            mXMENArray.add(XMEN[i]);

        this.mPager = (ViewPager) view.findViewById(R.id.viewpager);
        mPager.setAdapter(new SlideViewPagerAdapter(getContext(), this.mXMENArray));

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
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
                handler.post(Update);
            }
        }, 2500, 2500); // Interval: 2.5 seconds
    }

}
