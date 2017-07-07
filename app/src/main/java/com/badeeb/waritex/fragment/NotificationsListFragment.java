package com.badeeb.waritex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badeeb.waritex.R;
import com.badeeb.waritex.adapter.NotificationsRecyclerViewAdapter;
import com.badeeb.waritex.model.JsonResponse;
import com.badeeb.waritex.model.Notification;
import com.badeeb.waritex.model.NotificationsInquiry;
import com.badeeb.waritex.network.MyVolley;
import com.badeeb.waritex.shared.AppPreferences;
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
public class NotificationsListFragment extends Fragment {

    // Logging Purpose
    public static final String TAG = NotificationsListFragment.class.getSimpleName();

    // Class Attributes
    private SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mRecyclerView;
    private NotificationsRecyclerViewAdapter mAdapter;
    private List<Notification> mNotificationsList;

    // attributes that will be used for JSON calls
    private String mUrl = AppPreferences.BASE_URL + "/notifications";
    private int mcurrentPage;
    private int mpageSize;
    private boolean mNoMoreNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifications_list, container, false);

        init(view);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view) {

        Log.d(TAG, "init - Start");

        // Attribute initialization
        mNotificationsList = new ArrayList<>();
        // Swipe Container
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        // Recycler View creation
        mRecyclerView = (RecyclerView) view.findViewById(R.id.notifications_recycler_view);
        // Adapter creation
        mAdapter = new NotificationsRecyclerViewAdapter(getActivity(), mNotificationsList);
        // Layout Manager creation
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        // Link layout manager with recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Link adapter with recycler view
        mRecyclerView.setAdapter(mAdapter);

        // Call this method to setup listener on UI components
        setupListeners();

        // Make HTTP Call to get vendors list
        mcurrentPage = 1;
        mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
        mNoMoreNotifications = false;

        loadNotificationsDetails();

        Log.d(TAG, "init - End");
        
    }

    private void setupListeners() {

        Log.d(TAG, "setupListeners - Start");

        // Adding scroll to recycler view
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - Start");

                //check for scroll down
                if(dy > 0) {

                    if (mRecyclerView.getLayoutManager().findViewByPosition(mpageSize * mcurrentPage - 1) != null && ! mNoMoreNotifications) {
                        // Scrolling started to be near to end of list
                        // Load next page

                        Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - Load more notifications");

                        mcurrentPage++;
                        loadNotificationsDetails();
                    }
                }
                Log.d(TAG, "setupListeners - mRecyclerView:onScrolled - End");
            }
        });

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "setupListeners - mSwipeContainer:setOnRefreshListener - Start");

                mNotificationsList.clear();

                mcurrentPage = 1;
                mpageSize = AppPreferences.DEFAULT_PAGE_SIZE;
                mNoMoreNotifications = false;

                loadNotificationsDetails();

                Log.d(TAG, "setupListeners - mSwipeContainer:setOnRefreshListener - End");
            }
        });

        Log.d(TAG, "setupListeners - End");

    }

    private void loadNotificationsDetails() {

        Log.d(TAG, "loadNotificationsDetails - Start");

        // showing refresh animation before making http call
        mSwipeContainer.setRefreshing(true);

        String currentUrl = mUrl
                + "?page=" + mcurrentPage
                + "&page_size=" + mpageSize;

        Log.d(TAG, "loadNotificationsDetails - URL: " + currentUrl);

        // Call products service
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currentUrl, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Response Handling
                        Log.d(TAG, "loadNotificationsDetails - onResponse - Start");

                        Log.d(TAG, "loadNotificationsDetails - onResponse - Json Response: " + response.toString());

                        // Create Gson object
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                        Gson gson = gsonBuilder.create();

                        String responseData = response.toString();
                        Type responseType = new TypeToken<JsonResponse<NotificationsInquiry>>() {}.getType();

                        JsonResponse<NotificationsInquiry> jsonResponse = gson.fromJson(responseData, responseType);

                        Log.d(TAG, "loadNotificationsDetails - onResponse - Status: " + jsonResponse.getJsonMeta().getStatus());
                        Log.d(TAG, "loadNotificationsDetails - onResponse - Message: " + jsonResponse.getJsonMeta().getMessage());

                        // Move data to vendors list
                        if (jsonResponse.getResult().getNotifications() != null
                                && jsonResponse.getResult().getNotifications().size() != 0) {
                            mNotificationsList.addAll(jsonResponse.getResult().getNotifications());
                        }
                        else {
                            mNoMoreNotifications = true;
                        }

                        mAdapter.notifyDataSetChanged();

                        // stopping swipe refresh
                        mSwipeContainer.setRefreshing(false);

                        Log.d(TAG, "loadNotificationsDetails - onResponse - End");
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Network Error Handling
                        Log.d(TAG, "loadNotificationsDetails - onErrorResponse: " + error.toString());
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

        Log.d(TAG, "loadNotificationsDetails - End");
    }
}
