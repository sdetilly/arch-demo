package com.tillylabs.okhttpdemo.serviceLocator

import android.arch.lifecycle.LiveData
import com.tillylabs.okhttpdemo.WeatherData

/**
 * Created by steven on 2017-11-18.
 */
interface Service {
    fun fetchInformation() : LiveData<List<WeatherData>>
}