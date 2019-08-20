package com.goTenna.codingchallenge.data.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.goTenna.codingchallenge.data.model.LocationObject;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LocationDao {

    @Insert
    void insert(final LocationObject locationObject);

    @Query("SELECT * FROM location_table ORDER BY id ASC")
    Flowable<List<LocationObject>> getAllLocations();

}
