package com.goTenna.codingchallenge;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;
import com.goTenna.codingchallenge.viewmodel.LocationViewModel;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    public static final String DATASAVED = "DATA_SAVED";
    private LocationViewModel viewModel;
    private final List<Location> locationList = new ArrayList<>();
    private MapView mapView;
    private boolean isDataSaved;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        initializeViews();
        checkState(savedInstanceState);
        setMapView(savedInstanceState);
        setViewModel();
    }

    public void initializeViews() {
        mapView = findViewById(R.id.mapView);
    }

    public void checkState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isDataSaved = savedInstanceState.getBoolean(DATASAVED);
        }
    }

    public void setMapView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        }));
    }


    public void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        if (isDataSaved) {
            getLocations();
        } else {
            viewModel.deleteAllLocations();
            viewModel.callRetroFit();
            isDataSaved = true;
            getLocations();
        }
    }

    @SuppressLint("CheckResult")
    public void getLocations() {
        if (viewModel.getAllLocations() != null) {
            viewModel.getAllLocations()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(locations -> {
                        Log.d(LocationRepository.TAG, "RoomWithRx After Retrofit Call: " + locations.size());
                        locationList.addAll(locations);
                    }, throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DATASAVED, true);
        mapView.onSaveInstanceState(outState);
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
