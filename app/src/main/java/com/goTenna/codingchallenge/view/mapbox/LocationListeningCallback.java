package com.goTenna.codingchallenge.view.mapbox;

import android.location.Location;
import android.support.annotation.NonNull;

import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;

import java.lang.ref.WeakReference;

public class LocationListeningCallback implements LocationEngineCallback<LocationEngineResult> {
    private final WeakReference<MapActivity> activityWeakReference;

    public LocationListeningCallback(MapActivity mapActivity){
        this.activityWeakReference = new WeakReference<>(mapActivity);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        Location lastLocation = result.getLastLocation();
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
    }
}
