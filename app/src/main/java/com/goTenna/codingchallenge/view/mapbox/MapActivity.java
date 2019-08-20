package com.goTenna.codingchallenge.view.mapbox;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.view.MainActivity;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
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
        PermissionsListener, OnMapReadyCallback {
    public static final String TAG = "TAG";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationListeningCallback callback;
    private LocationComponent locationComponent;
    private FloatingActionButton fab;
    private boolean isTracking = false;

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
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style, lat, lng);
                style.addImage("marker-icon-id",
                        BitmapFactory.decodeResource(
                                MapActivity.this.getResources(), R.drawable.mapbox_marker_icon_default));

                GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                        Point.fromLngLat(lng, lat)));
                style.addSource(geoJsonSource);

                SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                symbolLayer.withProperties(
                        PropertyFactory.iconImage("marker-icon-id")
                );
                style.addLayer(symbolLayer);
            }
        });
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

    public void checkIntent() {
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
        fab = findViewById(R.id.compassfab);
        callback = new LocationListeningCallback(this);
    }

    public void setMapView(final Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    private void enableLocationComponent(Style style, double lat, double lng) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 16f));

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "MUST ALLOW PERMISSIONS FOR ACCESS", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style, lat, lng);
                }
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
