package com.goTenna.codingchallenge.data.repository;

import android.annotation.SuppressLint;
import android.app.Application;

import com.goTenna.codingchallenge.data.database.LocationDatabase;
import com.goTenna.codingchallenge.data.model.LocationObject;
import com.goTenna.codingchallenge.data.network.LocationRetroFit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocationRepository {
    private LocationDatabase locationDatabase;
    private static LocationRepository instance;
    private CompositeDisposable disposables = new CompositeDisposable();

    private LocationRepository(final Application application) {
        locationDatabase = LocationDatabase.getInstance(application.getApplicationContext());
    }

    public static LocationRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LocationRepository(application);
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    public Flowable<List<LocationObject>> getAllLocations() {
        return locationDatabase.locationDao().getAllLocations();
    }

    @SuppressLint("CheckResult")
    public void insertLocation(final LocationObject locationObject) {
//        Completable.fromAction(() -> locationDatabase.locationDao().insert(locationObject));

        Completable.fromRunnable(() -> locationDatabase.locationDao().insert(locationObject))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

//        locationDatabase.locationDao().insert(locationObject);
    }

    @SuppressLint("CheckResult")
    public void deleteAllLocations() {
        Completable.fromAction(() -> locationDatabase.clearAllTables());

        Completable.fromRunnable(() -> locationDatabase.clearAllTables())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

//        locationDatabase.clearAllTables();
    }


    @SuppressLint("CheckResult")
    public void callRetroFit() {
        LocationRetroFit
                .getService()
                .getLocation()
                .subscribeOn(Schedulers.io())
                .subscribe(locations -> {
                    if (locations != null) {
                        for (int i = 0; i < locations.size(); i++) {
                            insertLocation(locations.get(i));
                        }
                    }
                }, Throwable::printStackTrace);
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }

}

