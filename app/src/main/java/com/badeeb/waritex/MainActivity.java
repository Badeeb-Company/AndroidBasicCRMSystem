package com.badeeb.waritex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.badeeb.waritex.fragment.CompanyInfoFragment;
import com.badeeb.waritex.fragment.TabsFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Logging Purpose
    private final String TAG = MainActivity.class.getSimpleName();

    // Class attributes
    private Toolbar mtoolbar;
    private FragmentManager mFragmentManager;
    private TabsFragment mTabsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);

        setupDefaultLanguage();

        setContentView(R.layout.activity_main);

        init();

        Log.d(TAG, "onCreate - End");
    }

    private void setupDefaultLanguage() {

        Log.d(TAG, "setupDefaultLanguage - Start");

        String languageToLoad  = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        Log.d(TAG, "setupDefaultLanguage - End");
    }

    private void init() {

        Log.d(TAG, "init - Start");

        // Toolbar
        this.mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mtoolbar);

        mTabsFragment = new TabsFragment();

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, mTabsFragment, TabsFragment.TAG);
        fragmentTransaction.commit();

        Log.d(TAG, "init - End");
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
                    CompanyInfoFragment companyInfoFragment = new CompanyInfoFragment();
                    mFragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                    fragmentTransaction.add(R.id.main_frame, companyInfoFragment, companyInfoFragment.TAG);

                    fragmentTransaction.addToBackStack(TAG);

                    fragmentTransaction.commit();

                    Log.d(TAG, "onCreateOptionsMenu - onMenuItemClick - Start");
                    return false;
                }
            });
        }

        Log.d(TAG, "onCreateOptionsMenu - End");
        return true;
    }
}
