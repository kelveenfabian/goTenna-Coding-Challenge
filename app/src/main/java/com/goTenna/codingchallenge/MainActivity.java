package com.goTenna.codingchallenge;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.nfc.Tag;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;
import com.goTenna.codingchallenge.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private LocationViewModel viewModel;
    private final List<Location> locationList = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewModel();
        Log.d(LocationRepository.TAG, "RoomWithRx After ViewModel: " + locationList.size());
    }

    @SuppressLint("CheckResult")
    public void setViewModel(){
        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        viewModel.makeQuery().observe(this, locations -> {
            if(locations != null){
                for(int i = 0; i < locations.size(); i++){
                    viewModel.insertLocation(locations.get(i));
                }
            }
        });
        viewModel.getAllLocations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locations -> {
                    Log.d(LocationRepository.TAG, "RoomWithRx: " + locations.size());
                    locationList.addAll(locations);
                },throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.deleteAllLocations();
    }
}
