package com.goTenna.codingchallenge.data.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.goTenna.codingchallenge.data.model.Location;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface LocationDao {

    @Insert
    Completable insert(Location location);

    @Delete
    Completable delete(Location location);

    @Query("DELETE FROM location_table")
    Completable deleteAllLocations();

    @Query("SELECT * FROM location_table ORDER BY id ASC")
    Single<List<Location>> getAllLocations();
}
