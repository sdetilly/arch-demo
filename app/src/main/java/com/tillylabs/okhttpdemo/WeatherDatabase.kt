package com.tillylabs.okhttpdemo

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


/**
 * Created by steven on 2017-11-13.
 * The database used to store weatherData objects
 */
@Database(entities = arrayOf(WeatherData::class), version = 5)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        var mInstance: WeatherDatabase? = null
        fun getInstance(context: Context): WeatherDatabase {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "weather")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return mInstance!!
        }
    }

}