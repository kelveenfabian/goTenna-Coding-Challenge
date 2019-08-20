package com.goTenna.codingchallenge.data.network;

import com.goTenna.codingchallenge.data.model.Location;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LocationService {
    @GET("/development/scripts/get_map_pins.php")
    Flowable<List<Location>> getLocation();
}
