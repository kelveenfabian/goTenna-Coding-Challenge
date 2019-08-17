package com.goTenna.codingchallenge.network;

import com.goTenna.codingchallenge.data.model.Location;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LocationService {
    @GET("/development/scripts/get_map_pins.php")
    Observable<List<Location>> getLocation();
}
