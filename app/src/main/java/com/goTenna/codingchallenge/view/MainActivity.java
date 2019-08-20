package com.goTenna.codingchallenge.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;
import com.goTenna.codingchallenge.viewmodel.LocationViewModel;
import com.mapbox.android.core.permissions.PermissionsManager;
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

    private boolean isDataSaved;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        checkState(savedInstanceState);
        setViewModel();
    }

    public void initializeViews() {
    }

    public void checkState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isDataSaved = savedInstanceState.getBoolean(DATASAVED);
        }
    }


    public void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        if (isDataSaved) {
            getLocations();
            Log.d(LocationRepository.TAG, "RoomWithRx After SavedInstance");
        } else {
            viewModel.deleteAllLocations();
            viewModel.callRetroFit();
            isDataSaved = true;
            getLocations();
            Log.d(LocationRepository.TAG, "RoomWithRx After Retrofit Call");
        }
    }

    @SuppressLint("CheckResult")
    public void getLocations() {
        if (viewModel.getAllLocations() != null) {
            viewModel.getAllLocations()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(locations -> {
                        locationList.addAll(locations);
                    }, throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DATASAVED, true);
    }
}
