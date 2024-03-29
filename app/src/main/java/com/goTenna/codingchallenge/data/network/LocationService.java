package com.goTenna.codingchallenge.data.network;

import com.goTenna.codingchallenge.data.model.LocationObject;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface LocationService {
    @GET("/development/scripts/get_map_pins.php")
    Flowable<List<LocationObject>> getLocation();
}
