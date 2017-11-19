package com.tillylabs.okhttpdemo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData


/**
 * Created by steven on 2017-10-13.
 * Viewmodel for weather data list. Retains an instance of the data in case of config changes
 */

class WeatherVM(application: Application) : AndroidViewModel(application) {

    private var data = MediatorLiveData<List<WeatherData>>()

    private var appDatabase: WeatherDatabase = WeatherDatabase.getInstance(application)

    private var weatherRepo: WeatherRepo

    init {
        data.value = null
        weatherRepo = WeatherRepo.getInstance(appDatabase)
        val list = weatherRepo.weatherDataList
        data.addSource(list, { newData ->
            data.postValue(newData)
        })
    }

    fun getData(): LiveData<List<WeatherData>> {
        return data
    }

}
