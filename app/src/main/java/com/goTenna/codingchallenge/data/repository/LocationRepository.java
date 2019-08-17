package com.goTenna.codingchallenge.data.repository;

import android.app.Application;

import com.goTenna.codingchallenge.data.database.LocationDatabase;
import com.goTenna.codingchallenge.data.model.Location;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocationRepository {
    private LocationDatabase locationDatabase;
    private List<Location> locationList = new ArrayList<>();

    public LocationRepository(Application application){
        locationDatabase = LocationDatabase.getInstance(application.getApplicationContext());
    }

    public List<Location> getAllLocations(){
        locationDatabase.locationDao().getAllLocations()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Location>>() {
                    @Override
                    public void onSuccess(List<Location> locations) {
                        locationList.addAll(locations);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

        return locationList;
    }


}
