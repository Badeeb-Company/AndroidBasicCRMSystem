package com.badeeb.waritex;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.badeeb.waritex.adapter.ViewPagerAdapter;
import com.badeeb.waritex.fragment.ActivePromotionsFragment;
import com.badeeb.waritex.fragment.ExpiredPromotionsFragment;
import com.badeeb.waritex.fragment.ProductsFragment;

public class MainActivity extends AppCompatActivity {

    // Logging Purpose
    private final String TAG = MainActivity.class.getSimpleName();

    // Main Fragment attributes
    private Toolbar mtoolbar;
    private TabLayout mtabLayout;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        this.mtoolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(this.mtoolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ViewPager
        this.mviewPager = (ViewPager) this.findViewById(R.id.viewpager);
        this.setupViewPager(this.mviewPager);   // Defines the number of tabs by setting appropriate fragment and tab name

        // Tabs
        this.mtabLayout = (TabLayout) this.findViewById(R.id.tabs);
        this.mtabLayout.setupWithViewPager(this.mviewPager);        // Assigns the ViewPager to TabLayout.

        Log.d(TAG, "onCreate - End");
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ProductsFragment(), getString(R.string.products_tab_title));
        adapter.addFragment(new ActivePromotionsFragment(), getString(R.string.active_promotions_tab_title));
        adapter.addFragment(new ExpiredPromotionsFragment(), getString(R.string.expired_promotions_tab_title));

        viewPager.setAdapter(adapter);
    }
}
