package com.goTenna.codingchallenge.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.LocationObject;
import com.goTenna.codingchallenge.view.mapbox.MapActivity;
import com.goTenna.codingchallenge.view.recyclerview.LocationAdapter;
import com.goTenna.codingchallenge.viewmodel.LocationViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {
    public static final String DATASAVED = "DATA_SAVED";
    public static final String SAVESTATE = "SAVE_STATE";
    private LocationViewModel viewModel;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPrefs;
    private boolean isDataSavedInPrefs;
    private boolean isDataSaved;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        checkState(savedInstanceState);
        setViewModel();
    }

    public void initialize() {
        recyclerView = findViewById(R.id.locations_rv);
        sharedPrefs = getSharedPreferences(SAVESTATE, MODE_PRIVATE);
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    public void setRecyclerView(final List<LocationObject> locationObjectList) {
        LocationAdapter adapter = new LocationAdapter(locationObjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void checkState(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isDataSaved = savedInstanceState.getBoolean(DATASAVED);
        }
        if(sharedPrefs.contains(DATASAVED)){
            isDataSavedInPrefs = sharedPrefs.getBoolean(DATASAVED, false);
        }
    }

    public void setViewModel() {
        if (isDataSaved || isDataSavedInPrefs) {
            getLocations();
            Log.d(MapActivity.TAG, "SavedInstance");
        } else {
            viewModel.deleteAllLocations();
            viewModel.callRetroFit();
            isDataSaved = true;
            sharedPrefs.edit().putBoolean(DATASAVED, isDataSaved).apply();
            getLocations();
            Log.d(MapActivity.TAG, "RetroFitCall");
        }
    }

    @SuppressLint("CheckResult")
    public void getLocations() {
        if (viewModel.getAllLocations() != null) {
            viewModel.getAllLocations()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setRecyclerView, Throwable::printStackTrace);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(DATASAVED, true);
    }
}
