package com.badeeb.waritex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.model.CompanyInfo;
import com.badeeb.waritex.model.CompanyInfoInquiry;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CompanyInfoActivity extends AppCompatActivity {

    // For logging purpose
    private final String TAG = CompanyInfoActivity.class.getName();

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/company_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate - Start");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        
        init();

        Log.d(TAG, "onCreate - End");
    }

    private void init() {
        Log.d(TAG, "init - Start");

        loadCompanyInfo();

        Log.d(TAG, "init - End");
    }

    private void loadCompanyInfo() {
        Log.d(TAG, "loadCompanyInfo - Start");

        String currentUrl = mUrl;

        Log.d(TAG, "loadCompanyInfo - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadCompanyInfo - onResponse - Start");

                        Log.d(TAG, "loadCompanyInfo - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<CompanyInfoInquiry>>() {}.getType();

                        JsonResponse<CompanyInfoInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadCompanyInfo - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadCompanyInfo - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadCompanyInfo - onResponse - Company About: " + jsonResponse.getResult().getCompanyInfo().getAbout());

                        // Move data to company info object object
                        if (jsonResponse.getResult().getCompanyInfo() != null) {

                            updateCompanyInfoOnView(jsonResponse.getResult().getCompanyInfo());

                        }

                        Log.d(TAG, "loadCompanyInfo - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadCompanyInfo - onErrorResponse: " + error.toString());
                    }
                }
        ) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "*");
                return headers;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);

        Log.d(TAG, "loadCompanyInfo - End");
    }

    private void updateCompanyInfoOnView(CompanyInfo companyInfo) {
        Log.d(TAG, "updateCompanyInfoOnView - Start");

        TextView about = (TextView) findViewById(R.id.company_about_description);
        TextView telephone = (TextView) findViewById(R.id.company_telephone);
        TextView fax = (TextView) findViewById(R.id.company_fax);
//        TextView


        Log.d(TAG, "updateCompanyInfoOnView - End");
    }
}
