package com.badeeb.waritex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.SlideViewPagerAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Promotion;
import com.badeeb.waritex.model.PromotionDetailsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionDetailsFragment extends Fragment {

    // For logging purpose
    public static final String TAG = PromotionDetailsFragment.class.getName();

    private static ViewPager mPager;
    private Promotion mPromotion;

    // Constants
    public final static String EXTRA_PROMOTION_OBJECT = "EXTRA_PROMOTION_OBJECT";

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/promotions";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion_details, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {

        Log.d(TAG, "init - Start");

        // Get promotion details from previous activity
        mPromotion = Parcels.unwrap(getArguments().getParcelable(EXTRA_PROMOTION_OBJECT));

        // Attributes initialization
        mPager = (ViewPager) view.findViewById(R.id.promotion_details_viewpager);

        SlideViewPagerAdapter slideViewPagerAdapter = new SlideViewPagerAdapter(getContext(), mPromotion.getPhotos());
        mPager.setAdapter(slideViewPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.promotion_details_indicator);
        indicator.setViewPager(mPager);

        // Add listener to show vendors button
        Button button = (Button) view.findViewById(R.id.show_vendors_bttn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "init - onClick - Start");

                // Fragment creation
                VendorsListFragment vendorsListFragment = new VendorsListFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(VendorsListFragment.EXTRA_PROMOTION_ID, Parcels.wrap(mPromotion.getId()));
                vendorsListFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.add(R.id.main_frame, vendorsListFragment, vendorsListFragment.TAG);

                fragmentTransaction.addToBackStack(TAG);

                fragmentTransaction.commit();

                Log.d(TAG, "init - onClick - End");
            }
        });

        updatePromotionDetailsOnView(view);

        loadPromotionDetails(view);

        Log.d(TAG, "init - End");

    }

    private void loadPromotionDetails(final View view) {

        Log.d(TAG, "loadPromotionDetails - Start");

        String currentUrl = mUrl + "/" + mPromotion.getId();

        Log.d(TAG, "loadPromotionDetails - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadPromotionDetails - onResponse - Start");

                        Log.d(TAG, "loadPromotionDetails - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<PromotionDetailsInquiry>>() {}.getType();

                        JsonResponse<PromotionDetailsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadPromotionDetails - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadPromotionDetails - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadPromotionDetails - onResponse - Promotion Description: " + jsonResponse.getResult().getPromotion().getDescription());
                        Log.d(TAG, "loadPromotionDetails - onResponse - Photos Size: " + jsonResponse.getResult().getPromotion().getPhotos().size());

                        // Move data to promotion object
                        if (jsonResponse.getResult().getPromotion() != null) {

                            mPromotion.setTitle(jsonResponse.getResult().getPromotion().getTitle());
                            mPromotion.setDueDate(jsonResponse.getResult().getPromotion().getDueDate());
                            mPromotion.setDescription(jsonResponse.getResult().getPromotion().getDescription());
                            mPromotion.getPhotos().addAll(jsonResponse.getResult().getPromotion().getPhotos());

                            updatePromotionDetailsOnView(view);

                            mPager.getAdapter().notifyDataSetChanged();
                        }

                        Log.d(TAG, "loadPromotionDetails - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadPromotionDetails - onErrorResponse: " + error.toString());
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

        MyVolley.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

        Log.d(TAG, "loadPromotionDetails - End");
    }


    private void updatePromotionDetailsOnView(View view) {

        Log.d(TAG, "updatePromotionDetailsOnView - Start");

        // Find view elements and assign values to them
        // Promotion title
        Log.d(TAG, "updatePromotionDetailsOnView - Promotion Title: "+mPromotion.getTitle());
        TextView promotionTitleTV = (TextView) view.findViewById(R.id.promotion_details_title);
        promotionTitleTV.setText(mPromotion.getTitle());

        // Promotion due date
        Log.d(TAG, "updatePromotionDetailsOnView - Promotion Due Date: "+mPromotion.getDueDate());
        TextView promotionDueDateTV = (TextView) view.findViewById(R.id.promotion_details_due_date);
        promotionDueDateTV.setText(getResources().getText(R.string.valid_promotion_statment) + " " + mPromotion.getDueDate());

        // promotion_details_about

        // Promotion description
        Log.d(TAG, "updatePromotionDetailsOnView - Promotion Description: "+mPromotion.getDescription());
        TextView promotionDescriptionTV = (TextView) view.findViewById(R.id.promotion_details_description);
        promotionDescriptionTV.setText(mPromotion.getDescription());

        Log.d(TAG, "updatePromotionDetailsOnView - End");
    }

}
