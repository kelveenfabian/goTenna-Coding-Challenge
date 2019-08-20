package com.goTenna.codingchallenge.view.mapbox;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goTenna.codingchallenge.R;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;

public class MapActivity extends AppCompatActivity implements
        PermissionsListener, OnMapReadyCallback, OnLocationClickListener, OnCameraTrackingChangedListener {
    public static final String LAT = "LAT";
    public static final String LNG = "LNG";
    public static final String NAME = "NAME";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private FloatingActionButton fab;
    private boolean isTracking = false;
    private double lat;
    private double lng;
    private String name;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        initialize();
        checkIntent();
        checkState(savedInstanceState);
        setMapView(savedInstanceState);
    }

    private void initialize() {
        mapView = findViewById(R.id.mapView);
        fab = findViewById(R.id.compassfab);
    }

    private void checkIntent() {
        lat = getIntent().getDoubleExtra(LAT, 0.0);
        lng = getIntent().getDoubleExtra(LNG, 0.0);
        name = getIntent().getStringExtra(NAME);
    }

    private void checkState(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lat = savedInstanceState.getDouble(LAT);
            lng = savedInstanceState.getDouble(LNG);
            name = savedInstanceState.getString(NAME);
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.DARK, style -> {
            enableLocationComponent(style, lat, lng);
            createMarker();
        });
    }

    private void createMarker(){
        marker = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(name));
        marker.showInfoWindow(mapboxMap, mapView);
    }

    private void setMapView(final Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    void enableLocationComponent(Style style, double lat, double lng) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
           locationComponentSetup(style);
           mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16f));
           fabClick();

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    public void locationComponentSetup(Style style) {
        LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                .elevation(5)
                .accuracyAlpha(.6f)
                .build();
        locationComponent = mapboxMap.getLocationComponent();
        locationComponentActivation(style, customLocationComponentOptions);
    }

    @SuppressLint("MissingPermission")
    public void locationComponentActivation(Style style, LocationComponentOptions customLocationComponentOptions){
        LocationComponentActivationOptions locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, style)
                        .locationComponentOptions(customLocationComponentOptions)
                        .build();

        locationComponent.activateLocationComponent(locationComponentActivationOptions);
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setCameraMode(CameraMode.TRACKING_COMPASS);
        locationComponent.setRenderMode(RenderMode.COMPASS);
        locationComponent.addOnLocationClickListener(this);
        locationComponent.addOnCameraTrackingChangedListener(this);
    }

    public void fabClick(){
        fab.setOnClickListener(v -> {
            if (!isTracking) {
                isTracking = true;
                locationComponent.setCameraMode(CameraMode.TRACKING);
                locationComponent.zoomWhileTracking(16f);
                mapboxMap.removeMarker(marker);
            }else{
                Toast.makeText(MapActivity.this, "LOCATION ALREADY ENABLED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        lat = getIntent().getDoubleExtra(LAT, 0.0);
        lng = getIntent().getDoubleExtra(LNG, 0.0);
        name = getIntent().getStringExtra(NAME);

        outState.putDouble(LAT, lat);
        outState.putDouble(LNG, lng);
        outState.putString(NAME, name);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "MUST ALLOW PERMISSIONS FOR ACCESS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(style -> {
                enableLocationComponent(style, lat, lng);
                createMarker();
            });
        } else {
            Toast.makeText(this, "ACCESS DENIED", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onCameraTrackingDismissed() {
        isTracking = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {

    }

    @Override
    public void onLocationComponentClick() {

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

}
