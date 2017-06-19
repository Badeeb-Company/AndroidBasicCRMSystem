package com.badeeb.waritex.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.FragmentViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment extends Fragment {

    // Logging Purpose
    public static final String TAG = TabsFragment.class.getSimpleName();

    // Main Fragment attributes
    private TabLayout mtabLayout;
    private ViewPager mviewPager;

    public TabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate - End");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Start");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");
        return view;
    }

    private void init(View view) {
        Log.d(TAG, "init - Start");

        // Theme


        // ViewPager
        this.mviewPager = (ViewPager) view.findViewById(R.id.viewpager);
        this.setupViewPager(this.mviewPager);   // Defines the number of tabs by setting appropriate fragment and tab name

        // Tabs
        this.mtabLayout = (TabLayout) view.findViewById(R.id.tabs);
        this.mtabLayout.setupWithViewPager(this.mviewPager);        // Assigns the ViewPager to TabLayout.

        // Setup tab icons
        setupTabIcons();

        Log.d(TAG, "init - End");
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager - Start");

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getFragmentManager());

        adapter.addFragment(new ProductsFragment(), getString(R.string.products_tab_title));
        adapter.addFragment(new ActivePromotionsFragment(), getString(R.string.active_promotions_tab_title));
        adapter.addFragment(new ExpiredPromotionsFragment(), getString(R.string.expired_promotions_tab_title));

        viewPager.setAdapter(adapter);

        Log.d(TAG, "setupViewPager - End");
    }

    private void setupTabIcons() {

        Log.d(TAG, "setupTabIcons - End");

        this.mtabLayout.getTabAt(0).setIcon(R.drawable.product_icon);
        this.mtabLayout.getTabAt(1).setIcon(R.drawable.promotion_icon);
        this.mtabLayout.getTabAt(2).setIcon(R.drawable.promotion_icon);

        Log.d(TAG, "setupTabIcons - End");
    }
}
