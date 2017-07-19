package com.badeeb.waritex.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import org.parceler.Parcels;

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
    private SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mRecyclerView;
    private PromotionsRecyclerViewAdaptor mAdapter;
    private List<Promotion> mActivePromotionList;
    private int mPromotionPerLine;

    // attributes that will be used for JSON calls
    private String url = AppPreferences.BASE_URL + "/promotions";
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMorePromotions;

    public ActivePromotionsFragment() {

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
        mPromotionPerLine = AppPreferences.ONE_VIEW_IN_LINE;
        mActivePromotionList = new ArrayList<Promotion>();



        // Swipe Container
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        // Recycler View creation
        mRecyclerView = (RecyclerView) view.findViewById(R.id.active_promotion_recycler_view);
        // Adapter creation
        mAdapter = new PromotionsRecyclerViewAdaptor(getActivity(), mActivePromotionList,false);
        // Layout Manager creation
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), mPromotionPerLine);
        // Link layout manager with recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Link adapter with recycler view
        mRecyclerView.setAdapter(mAdapter);


        // Call this method to setup listener on UI components
        setupListeners();

        this.mcurrentPage = 1;
        this.mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        this.mNoMorePromotions = false;

        loadPromotionsDetails();

        Log.d(TAG, "init - End");
    }

    private void loadPromotionsDetails() {

        Log.d(TAG, "loadPromotionsDetails - Start");

        // showing refresh animation before making http call
        mSwipeContainer.setRefreshing(true);

        String currentUrl = url + "?expired=false&page=" + mcurrentPage + "&page_size=" + mpageSize;

        Log.d(TAG, "loadPromotionsDetails - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadPromotionsDetails - onResponse - Start");

                        Log.d(TAG, "loadPromotionsDetails - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<PromotionsInquiry>>() {}.getType();

                        JsonResponse<PromotionsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadPromotionsDetails - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadPromotionsDetails - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());
                        Log.d(TAG, "loadPromotionsDetails - onResponse - Data Size: " + jsonResponse.getResult().getPromotions().size());

                        // Load Data into slide show
                        if (jsonResponse.getResult().getPromotions()!= null
                                && jsonResponse.getResult().getPromotions().size() != 0) {
                            mActivePromotionList.addAll(jsonResponse.getResult().getPromotions());
                        }
                        else {
                            // All Promotions are loaded
                            mNoMorePromotions = true;
                        }

                        mAdapter.notifyDataSetChanged();

                        // stopping swipe refresh
                        mSwipeContainer.setRefreshing(false);

                        Log.d(TAG, "loadPromotionsDetails - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadPromotionsDetails - onErrorResponse: " + error.toString());
                        // stopping swipe refresh
                        mSwipeContainer.setRefreshing(false);
                        // Display error Message to user
                        if (AppPreferences.isNetworkAvailable(getContext())) {
                            // Internet is available but server not
                            Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Server is not available
                            Toast.makeText(getContext(), getString(R.string.internet_service_message), Toast.LENGTH_SHORT).show();
                        }
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

        // Adding retry policy to request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(AppPreferences.VOLLEY_TIME_OUT, AppPreferences.VOLLEY_RETRY_COUNTER, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyVolley.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

        Log.d(TAG, "loadPromotionsDetails - End");
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void setupListeners() {

        Log.d(TAG, "setupListeners - Start");

        // Adding OnItemTouchListener to recycler view
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Log.d(TAG, "setupListeners - mRecyclerView:onItemClick - Start");

                        // Get item that is selected
                        Promotion promotionSelected = mActivePromotionList.get(position);

                        // Fragment creation
                        PromotionDetailsFragment promotionDetailsFragment = new PromotionDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(PromotionDetailsFragment.EXTRA_PROMOTION_OBJECT, Parcels.wrap(promotionSelected));
                        bundle.putBoolean(PromotionDetailsFragment.EXTRA_PROMOTION_IS_EXPIRED, false);
                        promotionDetailsFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.add(R.id.main_frame, promotionDetailsFragment, promotionDetailsFragment.TAG);
//                        fragmentTransaction.replace(R.id.main_frame, promotionDetailsFragment);

                        fragmentTransaction.addToBackStack(TAG);

                        fragmentTransaction.commit();

                        Log.d(TAG, "setupListeners - onItemClick - End");
                    }
                })
        );

        // Adding scroll to recycler view
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - Start");

                //check for scroll down
                if(dy > 0) {

                    if (mRecyclerView.getLayoutManager().findViewByPosition(mpageSize * mcurrentPage - 1) != null && ! mNoMorePromotions) {
                        // Scrolling started to be near to end of list
                        // Load next page

                        Log.d(TAG, "init - mRecyclerView:onScrolled - Load more promotions");

                        mcurrentPage++;
                        loadPromotionsDetails();
                    }
                }
                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - End");
            }
        });

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "setupListeners - mSwipeContainer:setOnRefreshListener - Start");

                mActivePromotionList.clear();

                mcurrentPage = 1;
                mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
                mNoMorePromotions = false;

                loadPromotionsDetails();

                Log.d(TAG, "setupListeners - mSwipeContainer:setOnRefreshListener - End");
            }
        });

        Log.d(TAG, "setupListeners - End");
    }
}
