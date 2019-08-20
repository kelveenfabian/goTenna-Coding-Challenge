package com.goTenna.codingchallenge.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationRetroFit {
    private static Retrofit instance;

    private LocationRetroFit() {
    }

    private static Retrofit getClient() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl("https://annetog.gotenna.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public static LocationService getService() {
        return getClient().create(LocationService.class);
    }
}
