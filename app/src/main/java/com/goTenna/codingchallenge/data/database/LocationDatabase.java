package com.goTenna.codingchallenge.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.goTenna.codingchallenge.data.model.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    private static LocationDatabase instance;

    private LocationDatabase(){}

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class,"location_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
 }
