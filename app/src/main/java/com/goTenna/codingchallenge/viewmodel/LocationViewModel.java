package com.goTenna.codingchallenge.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;


public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = LocationRepository.getInstance(application);
    }

    public void insertLocation(Location location) {
        repository.insertLocation(location);
    }

    public void deleteLocation(Location location) {
        repository.deleteLocation(location);
    }

    public void deleteAllLocations() {
        repository.deleteAllLocations();
    }

    public Flowable<List<Location>> getAllLocations() {
        return repository.getAllLocations();
    }

    public List<Location> isEmpty(){
        return repository.isEmpty();
    }

    public void callRetroFit() {
        repository.callRetroFit();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.getDisposables().clear();
    }

}
