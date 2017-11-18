package com.tillylabs.okhttpdemo

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase



/**
 * Created by steven on 2017-11-13.
 */
@Database(entities = arrayOf(WeatherData::class), version = 5)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}