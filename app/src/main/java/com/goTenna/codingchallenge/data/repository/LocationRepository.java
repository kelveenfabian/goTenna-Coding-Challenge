package com.goTenna.codingchallenge.data.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;

import com.goTenna.codingchallenge.data.database.LocationDatabase;
import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.network.LocationRetroFit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocationRepository {
    private LocationDatabase locationDatabase;
    public static final String TAG = "TAG";
    private static LocationRepository instance;
    private CompositeDisposable disposables = new CompositeDisposable();

    private LocationRepository(Application application){
        locationDatabase = LocationDatabase.getInstance(application.getApplicationContext());
    }

    public static LocationRepository getInstance(Application application){
        if(instance == null){
            instance = new LocationRepository(application);
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    public Flowable<List<Location>> getAllLocations(){
        return locationDatabase.locationDao().getAllLocations();
    }

    @SuppressLint("CheckResult")
    public void insertLocation(final Location location){
//        Completable.fromAction(() -> locationDatabase.locationDao().insert(location));

        Completable.fromRunnable(() -> locationDatabase.locationDao().insert(location))
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

//        locationDatabase.locationDao().insert(location);
    }

    @SuppressLint("CheckResult")
    public void deleteLocation(final Location location){
//        Completable.fromAction(() -> locationDatabase.locationDao().delete(location));

        Completable.fromRunnable(() -> locationDatabase.locationDao().delete(location))
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

//        locationDatabase.locationDao().delete(location);
    }

    @SuppressLint("CheckResult")
    public void deleteAllLocations(){
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


    public LiveData<List<Location>> getLocationsFromApi(){
        return LiveDataReactiveStreams.fromPublisher(LocationRetroFit
                .getService()
                .getLocation()
                .observeOn(Schedulers.io()));
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }

}

