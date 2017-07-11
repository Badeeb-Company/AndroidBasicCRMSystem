package com.badeeb.waritex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.badeeb.waritex.fragment.CompanyInfoFragment;
import com.badeeb.waritex.fragment.NotificationsListFragment;
import com.badeeb.waritex.fragment.ProductsFragment;
import com.badeeb.waritex.fragment.PromotionDetailsFragment;
import com.badeeb.waritex.fragment.TabsFragment;
import com.badeeb.waritex.model.Promotion;
import com.badeeb.waritex.shared.AppPreferences;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;
import org.parceler.Parcels;

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

        // Add Fabric to project
        Fabric.with(this, new Crashlytics());

        setupDefaultLanguage();

        setContentView(R.layout.activity_main);

        init();

        Log.d(TAG, "onCreate - End");
    }

    private void setupDefaultLanguage() {

        Log.d(TAG, "setupDefaultLanguage - Start");

        // Default Language is Arabic
        String languageToLoad  = "ar";

        // Get language boolean value from shared preferences
        SharedPreferences prefs = AppPreferences.getAppPreferences(this);
        boolean englishEnabled = prefs.getBoolean(AppPreferences.PREF_ENGLISH_ENABLED, false);

        if (englishEnabled) {
            // English Language
            languageToLoad = "en";
        }

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
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);

            setupTitleListeners(customView);

            // Apply the custom view
            actionBar.setCustomView(customView);
        }

        mTabsFragment = new TabsFragment();

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, mTabsFragment, TabsFragment.TAG);
        fragmentTransaction.commit();

        // Get notification boolean value from shared preferences
        SharedPreferences prefs = AppPreferences.getAppPreferences(this);
        boolean notificationEnabled = prefs.getBoolean(AppPreferences.PREF_NOTIFICATION_ENABLED, true);
        if (notificationEnabled) {
            // subscribe to Firebase topic
            FirebaseMessaging.getInstance().subscribeToTopic(AppPreferences.TOPIC_NAME);
        }

        // Handling Firebase dynamic links
        handleFirebaseDynamicLinks();

        Log.d(TAG, "init - End");
    }

    private void replaceFragmentAsHome(Fragment fragment){
        String backStateName = fragment.getClass().getSimpleName();
        mFragmentManager = getSupportFragmentManager();

        // clear the fragements history stack
        for(int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStack();
        }

        // Add the fragment again
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.addToBackStack(backStateName);
        ft.commit();

    }

    private void setupTitleListeners(View customView) {
        // Get the textview of the title
        TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);

        // Set the on click listener for the title
        customTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClickCustmTitle TextView - tabsFragment:homeButton - Display fragment - start");

                TabsFragment tabsFragment = (TabsFragment) mFragmentManager.findFragmentByTag(TabsFragment.TAG);

                // Move to tabs fragment
                replaceFragmentAsHome(tabsFragment);

                Log.d(TAG, "onClickCustmTitle TextView - tabsFragment:homeButton - Display fragment - end");

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu - Start");

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem companyInfo = menu.findItem(R.id.action_company_info);
        MenuItem notificationsHistory = menu.findItem(R.id.action_notifications_history);


        if (companyInfo != null) {
         // Add onclick listener on button
            companyInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onCreateOptionsMenu - companyInfo:onMenuItemClick - Start");

                    // Check first if company info fragment is the one displayed now in screen or not
                    CompanyInfoFragment companyInfoFragment = (CompanyInfoFragment) mFragmentManager.findFragmentByTag(CompanyInfoFragment.TAG);

                    if (companyInfoFragment == null || companyInfoFragment.isVisible() == false) {
                        // Move to company info activity
                        Log.d(TAG, "onCreateOptionsMenu - companyInfo:onMenuItemClick - Display fragment");

                        companyInfoFragment = new CompanyInfoFragment();
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.main_frame, companyInfoFragment, companyInfoFragment.TAG);

                        fragmentTransaction.addToBackStack(TAG);

                        fragmentTransaction.commit();
                    }

                    Log.d(TAG, "onCreateOptionsMenu - companyInfo:onMenuItemClick - Start");
                    return false;
                }
            });
        }

        if (notificationsHistory != null) {
            // Add onclick listener on button
            notificationsHistory.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.d(TAG, "onCreateOptionsMenu - notificationsHistory:onMenuItemClick - Start");

                    // Check first if company info fragment is the one displayed now in screen or not
                    NotificationsListFragment notificationsListFragment = (NotificationsListFragment) mFragmentManager.findFragmentByTag(NotificationsListFragment.TAG);

                    if (notificationsListFragment == null || notificationsListFragment.isVisible() == false) {
                        // Move to company info activity
                        Log.d(TAG, "onCreateOptionsMenu - notificationsHistory:onMenuItemClick - Display fragment");

                        notificationsListFragment = new NotificationsListFragment();
                        mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.main_frame, notificationsListFragment, notificationsListFragment.TAG);

                        fragmentTransaction.addToBackStack(TAG);

                        fragmentTransaction.commit();
                    }

                    Log.d(TAG, "onCreateOptionsMenu - notificationsHistory:onMenuItemClick - Start");
                    return false;
                }
            });
        }

        Log.d(TAG, "onCreateOptionsMenu - End");
        return true;
    }

    private void handleFirebaseDynamicLinks() {

        Log.d(TAG, "handleFirebaseDynamicLinks - Start");

        FirebaseDynamicLinks firebaseDynamicLinks = FirebaseDynamicLinks.getInstance();

        Task<PendingDynamicLinkData> pendingDynamicLinkDataTask = firebaseDynamicLinks.getDynamicLink(getIntent());

        pendingDynamicLinkDataTask.addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - Start");

                Uri deepLink = null;
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.getLink();

                    Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - DeepLink: "+deepLink);
                    // Get deep link attributes
                    String promotionId = deepLink.getQueryParameter("promotion_id");

                    if (promotionId != null) {
                        
                        Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - Open Promotion Start");

                        // Open promotion details fragment
                        Promotion promotionSelected = new Promotion();
                        promotionSelected.setId(Integer.parseInt(promotionId));

                        // Fragment creation
                        PromotionDetailsFragment promotionDetailsFragment = new PromotionDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PromotionDetailsFragment.EXTRA_PROMOTION_OBJECT, Parcels.wrap(promotionSelected));
                        promotionDetailsFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.main_frame, promotionDetailsFragment, promotionDetailsFragment.TAG);
//                        fragmentTransaction.replace(R.id.main_frame, promotionDetailsFragment);

                        fragmentTransaction.addToBackStack(TAG);

                        fragmentTransaction.commit();

                        Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - Open Promotion End");
                    }


                }
                else {
                    Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - pendingDynamicLinkData = null");
                }

                Log.d(TAG, "handleFirebaseDynamicLinks - onSuccess - End");
            }
        });

        pendingDynamicLinkDataTask.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "handleFirebaseDynamicLinks - onFailure - Start");

                Log.w(TAG, "handleFirebaseDynamicLinks - onFailure - Exception: ", e);

                Log.d(TAG, "handleFirebaseDynamicLinks - onFailure - End");
            }
        });
    }

}
