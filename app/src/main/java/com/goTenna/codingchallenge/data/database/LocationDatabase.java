package com.goTenna.codingchallenge.data.database;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    private static LocationDatabase instance;

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class,"location_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    @SuppressLint("CheckResult")
    public List<Location> getAllLocations(){
        final List<Location> locationList = new ArrayList<>();

        locationDao().getAllLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Location>>() {
                    @Override
                    public void accept(List<Location> locations) throws Exception {
                        locationList.addAll(locations);
                    }
                });

        return locationList;
    }

    @SuppressLint("CheckResult")
    public void insertLocation(final Location location){
        Completable.fromAction(() -> locationDao().insert(location));
        Log.d(LocationRepository.TAG, location.getName() + " is inside of database");
    }

    @SuppressLint("CheckResult")
    public void deleteLocation(final Location location){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                locationDao().delete(location);
            }
        });
    }

    public void deleteAllLocations(){
        locationDao().deleteAllLocations();
    }
 }
