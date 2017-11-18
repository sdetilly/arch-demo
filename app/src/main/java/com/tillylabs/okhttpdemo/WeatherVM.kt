package com.tillylabs.okhttpdemo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.persistence.room.Room


/**
 * Created by steven on 2017-10-13.
 *
 */

class WeatherVM(application: Application) : AndroidViewModel(application) {

    private var data = MediatorLiveData<List<WeatherData>>()

    private var appDatabase: WeatherDatabase = Room.databaseBuilder(application,
            WeatherDatabase::class.java, "weather").fallbackToDestructiveMigration().build()

    private var weatherRepo: WeatherRepo

    init {
        data.value = null
        weatherRepo = WeatherRepo.getInstance(appDatabase)
        val list = weatherRepo.weatherDataList
        data.addSource(list, data::setValue)
    }

    fun getData(): LiveData<List<WeatherData>> {
        return data
    }

}
