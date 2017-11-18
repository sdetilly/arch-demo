package com.tillylabs.okhttpdemo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query


/**
 * Created by steven on 2017-11-13.
 */
@Dao
interface WeatherDao {
    @get:Query("SELECT * FROM weatherData")
    val all: LiveData<List<WeatherData>>

    /*@Query("SELECT * FROM weatherData WHERE cityName IN :cityName")
    fun loadAllByName(cityNames: List<String>): List<WeatherData>*/

    @Query("SELECT * FROM weatherData WHERE cityName LIKE :name")
    fun findByName(name: String): WeatherData

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg dataSet: WeatherData)

    @Delete
    fun delete(data: WeatherData)
}