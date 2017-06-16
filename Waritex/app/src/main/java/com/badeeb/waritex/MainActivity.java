package com.badeeb.waritex;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.badeeb.waritex.adapter.FragmentViewPagerAdapter;
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

        // ViewPager
        this.mviewPager = (ViewPager) this.findViewById(R.id.viewpager);
        this.setupViewPager(this.mviewPager);   // Defines the number of tabs by setting appropriate fragment and tab name

        // Tabs
        this.mtabLayout = (TabLayout) this.findViewById(R.id.tabs);
        this.mtabLayout.setupWithViewPager(this.mviewPager);        // Assigns the ViewPager to TabLayout.

        Log.d(TAG, "onCreate - End");
    }

    private void setupViewPager(ViewPager viewPager) {

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ProductsFragment(), getString(R.string.products_tab_title));
        adapter.addFragment(new ActivePromotionsFragment(), getString(R.string.active_promotions_tab_title));
        adapter.addFragment(new ExpiredPromotionsFragment(), getString(R.string.expired_promotions_tab_title));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu - Start");

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem companyInfo = menu.findItem(R.id.action_company_info);

        if (companyInfo != null) {
            // Add onclick listener on button
            companyInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onCreateOptionsMenu - onMenuItemClick - Start");

                    // Move to company info activity
                    Intent intent = new Intent(getBaseContext(), CompanyInfoActivity.class);
                    startActivity(intent);

                    Log.d(TAG, "onCreateOptionsMenu - onMenuItemClick - Start");
                    return false;
                }
            });
        }

        Log.d(TAG, "onCreateOptionsMenu - End");
        return true;
    }
}
