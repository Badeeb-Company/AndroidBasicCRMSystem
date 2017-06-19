package com.badeeb.waritex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    // Logging Purpose
    public static final String TAG = MapFragment.class.getSimpleName();

    // Class Attributes
    private GoogleMap mMap;
    private MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        Log.d(TAG, "onCreateView - Start");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        init(view, savedInstanceState);

        Log.d(TAG, "onCreateView - End");

        return view;
    }

    private void init(View view, Bundle savedInstanceState) {

        Log.d(TAG, "init - Start");

        // Map Initialization
        MapsInitializer.initialize(getContext());
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        Log.d(TAG, "init - End");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //----------------------------------------------------------------------------------------------
    // Following methods are called to handle mapView lifecycle

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    //----------------------------------------------------------------------------------------------

}
