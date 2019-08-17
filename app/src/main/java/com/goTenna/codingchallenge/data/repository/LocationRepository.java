package com.goTenna.codingchallenge.data.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.goTenna.codingchallenge.MainActivity;
import com.goTenna.codingchallenge.data.database.LocationDatabase;
import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.network.LocationRetroFit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationRepository {
    private LocationDatabase locationDatabase;
    public static final String TAG = "TAG";

    public LocationRepository(Application application){
        locationDatabase = LocationDatabase.getInstance(application.getApplicationContext());
    }


    public List<Location> getAllLocations(){
        return locationDatabase.getAllLocations();
    }

    public void insertLocation(final Location location){
        locationDatabase.insertLocation(location);
    }

    public void deleteAllLocations(){
        locationDatabase.deleteAllLocations();
    }

    @SuppressLint("CheckResult")
    public void deleteLocation(final Location location){
        locationDatabase.locationDao().delete(location);
    }


    @SuppressLint("CheckResult")
    public void callRetroFit(){
        LocationRetroFit.getService()
                .getLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locations -> {
                    for(int i = 0; i < locations.size(); i++){
                        Log.d(TAG, "Location " + locations.get(i).getName());
                        insertLocation(locations.get(i));
                    }
                    Log.d(TAG, "RetroFit: LocationSize: " + locations.size());
                }, Throwable::printStackTrace);
    }


}
