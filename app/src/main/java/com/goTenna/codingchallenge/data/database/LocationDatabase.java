package com.goTenna.codingchallenge.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.goTenna.codingchallenge.data.model.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    private static LocationDatabase instance;
    private static final String DB_NAME = "location_db";

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
 }
