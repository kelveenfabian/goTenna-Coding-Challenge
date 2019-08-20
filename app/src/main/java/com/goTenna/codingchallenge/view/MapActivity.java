package com.goTenna.codingchallenge.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.repository.LocationRepository;
import com.goTenna.codingchallenge.view.recyclerview.LocationVH;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

public class MapActivity extends AppCompatActivity implements PermissionsListener {
    public static final String TAG = "TAG";
    private MapView mapView;
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        initialize();
        checkIntent();
        checkState(savedInstanceState);
        setMapView(savedInstanceState);
        Log.d(TAG, "Lat: " + lat);
        Log.d(TAG, "Lng: " + lng);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        lat = getIntent().getDoubleExtra(LAT, 0.0);
        lng = getIntent().getDoubleExtra(LNG, 0.0);
        outState.putDouble(LAT, lat);
        outState.putDouble(LNG, lng);
    }

    public void checkIntent(){
        lat = getIntent().getDoubleExtra(LAT, 0.0);
        lng = getIntent().getDoubleExtra(LNG, 0.0);
    }

    public void checkState(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lat = savedInstanceState.getDouble(LAT);
            lng = savedInstanceState.getDouble(LNG);
        }
    }

    public void initialize() {
        mapView = findViewById(R.id.mapView);
    }

    public void setMapView(final Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }
}
