package com.goTenna.codingchallenge.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.goTenna.codingchallenge.data.model.LocationObject;
import com.goTenna.codingchallenge.data.repository.LocationRepository;

import java.util.List;

import io.reactivex.Flowable;


public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;

    public LocationViewModel(@NonNull final Application application) {
        super(application);
        repository = LocationRepository.getInstance(application);
    }

    public void deleteAllLocations() {
        repository.deleteAllLocations();
    }

    public Flowable<List<LocationObject>> getAllLocations() {
        return repository.getAllLocations();
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
