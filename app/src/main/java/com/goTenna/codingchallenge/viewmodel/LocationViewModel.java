package com.goTenna.codingchallenge.viewmodel;

import android.app.Application;

import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.data.repository.LocationRepository;

import java.util.List;


public class LocationViewModel {
    private static final String TAG = "TAG";
    private LocationRepository locationRepository;
    private List<Location> locationList;


    public LocationViewModel(Application application){
        locationRepository = new LocationRepository(application);
        locationList = locationRepository.getAllLocations();
    }

    public List<Location> getLocationList() {
        return locationList;
    }






}
