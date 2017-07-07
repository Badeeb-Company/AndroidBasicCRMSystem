package com.badeeb.waritex.shared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Amr Alghawy on 6/12/2017.
 */

public class AppPreferences {

    // For logging purpose
    public static final String TAG = AppPreferences.class.getName();

    public static final String BASE_URL = "https://safe-bastion-53717.herokuapp.com/api/v1";
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String GOOGLE_MAP_API_KEY = "AIzaSyA2KWlCI-TJV1sIpycjlhVVHnTMjXUHRrI";
    public static final String DATE_FORMAT_DISPLAY_PATTERN = "dd-MMM-yyyy";
    public static final String DATE_FORMAT_WRITE_PATTERN = "dd-MMM-yyyy";
    public static final int TWO_VIEWS_IN_LINE = 2; // Default Value
    public static final int ONE_VIEW_IN_LINE = 1; // Default Value
    // Volley constants
    public static final int VOLLEY_TIME_OUT = 2000; // Milliseconds
    public static final int VOLLEY_RETRY_COUNTER = 2;
    // App URL on Play store
    public static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.badeeb.waritex";

    // Firebase constants
    public static final String TOPIC_NAME = "waritex";

    // Shared Preferences Keys
    public static final String PREF_NOTIFICATION_ENABLED = "key.notification.enabled"; //boolean
    public static final String PREF_ENGLISH_ENABLED = "key.english.enabled"; //boolean

    public static SharedPreferences getAppPreferences(Context context) {
        return context.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
