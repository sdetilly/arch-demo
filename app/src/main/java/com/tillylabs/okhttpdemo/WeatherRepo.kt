package com.tillylabs.okhttpdemo

import android.arch.lifecycle.MediatorLiveData


/**
 * Created by steven on 2017-11-18.
 */
class WeatherRepo(db: WeatherDatabase) {
    val weatherDataList = MediatorLiveData<List<WeatherData>>()

    init {
        weatherDataList.addSource(db.weatherDao().all, { data ->
            weatherDataList.postValue(data)
        })
    }

    companion object {
        var mInstance: WeatherRepo? = null
        fun getInstance(db: WeatherDatabase): WeatherRepo{
            if(mInstance == null){
                synchronized(WeatherRepo::class){
                    if(mInstance == null){
                        mInstance = WeatherRepo(db)
                    }
                }
            }
            return mInstance!!
        }
    }
}