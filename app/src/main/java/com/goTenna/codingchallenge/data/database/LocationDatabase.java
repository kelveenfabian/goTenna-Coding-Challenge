package com.goTenna.codingchallenge.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.goTenna.codingchallenge.data.model.LocationObject;

@Database(entities = {LocationObject.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    private static LocationDatabase instance;
    private static final String DB_NAME = "location_db";

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
