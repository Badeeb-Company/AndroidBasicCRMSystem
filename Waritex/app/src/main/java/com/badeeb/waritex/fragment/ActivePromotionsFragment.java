package com.badeeb.waritex.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.PromotionDetailsActivity;
import com.badeeb.waritex.listener.RecyclerItemClickListener;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Promotion;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.PromotionsRecyclerViewAdaptor;
import com.badeeb.waritex.model.PromotionsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
import com.badeeb.waritex.view.GridSpacingItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivePromotionsFragment extends Fragment {

    // Logging Purpose
    private final String TAG = ActivePromotionsFragment.class.getSimpleName();

    // Class Attributes
    private RecyclerView mRecyclerView;
    private PromotionsRecyclerViewAdaptor mAdapter;
    private List<Promotion> mActivePromotionList;
    private int mPromotionPerLine;

    public final static String PROMOTION_TITLE_EXTRA = "promotion_title_extra";
    public final static String PROMOTION_DUE_DATE_EXTRA = "promotion_due_date_extra";

    // attributes that will be used for JSON calls
    private String url = AppPreferences.BASE_URL + "/promotions";
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMorePromotions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPromotionPerLine = 2;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_active_promotions, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {

        Log.d(TAG, "init - Start");

        // Attribute initialization
        mActivePromotionList = new ArrayList<Promotion>();

        // Recycler View creation
        mRecyclerView = (RecyclerView) view.findViewById(R.id.active_promotion_recycler_view);
        // Adapter creation
        mAdapter = new PromotionsRecyclerViewAdaptor(getActivity(), mActivePromotionList);
        // Layout Manager creation
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), mPromotionPerLine);
        // Link layout manager with recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Link adapter with recycler view
        mRecyclerView.setAdapter(mAdapter);

        // Adding OnItemTouchListener to recycler view
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), PromotionDetailsActivity.class);

                        // TODO replace it with sending promotion object that will implement Parceable
                        TextView titleTV = (TextView) view.findViewById(R.id.promotion_card_title);
                        String title = titleTV.getText().toString();
                        intent.putExtra(PROMOTION_TITLE_EXTRA, title);

                        TextView dueDateTV = (TextView) view.findViewById(R.id.promotion_card_due_date);
                        String dueDate = dueDateTV.getText().toString();
                        intent.putExtra(PROMOTION_DUE_DATE_EXTRA, dueDate);

                        startActivity(intent);
                    }
                })
        );

        // Adding scroll to recycler view
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d(TAG, "init - onScrolled - Start");

                //check for scroll down
                if(dy > 0) {

                    if (mRecyclerView.getLayoutManager().findViewByPosition(mpageSize * mcurrentPage - 1) != null && ! mNoMorePromotions) {
                        // Scrolling started to be near to end of list
                        // Load next page

                        Log.d(TAG, "init - onScrolled - Load more promotions");

                        mcurrentPage++;
                        loadPromotions();
                    }
                }
                Log.d(TAG, "init - onScrolled - End");
            }
        });

        this.mcurrentPage = 1;
        this.mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        this.mNoMorePromotions = false;

        loadPromotions();

        Log.d(TAG, "init - End");
    }

    private void loadPromotions() {

        Log.d(TAG, "loadPromotions - Start");

        String currentUrl = url + "?expired=false&page=" + mcurrentPage + "&page_size=" + mpageSize;

        Log.d(TAG, "loadPromotions - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadPromotions - onResponse - Start");

                        Log.d(TAG, "loadPromotions - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<PromotionsInquiry>>() {}.getType();

                        JsonResponse<PromotionsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadPromotions - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadPromotions - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadPromotions - onResponse - Data Size: " + jsonResponse.getResult().getPromotions().size());

                        // Load Data into slide show
                        if (jsonResponse.getResult().getPromotions().size() != 0) {
                            mActivePromotionList.addAll(jsonResponse.getResult().getPromotions());
                        }
                        else {
                            // All Promotions are loaded
                            mNoMorePromotions = true;
                        }

                        mAdapter.notifyDataSetChanged();

                        Log.d(TAG, "loadPromotions - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadPromotions - onErrorResponse: " + error.toString());
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

        Log.d(TAG, "loadPromotions - End");
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
