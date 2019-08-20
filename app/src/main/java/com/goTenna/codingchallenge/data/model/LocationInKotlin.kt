package com.goTenna.codingchallenge.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "location_table")
class Location(var name: String?, var latitude: Double, var longitude: Double, var description: String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
