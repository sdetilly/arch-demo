package com.tillylabs.okhttpdemo

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.experimental.launch

/**
 * Created by steven on 2017-11-16.
 */
class MyJobScheduler: JobService() {

    private lateinit var appDatabase: WeatherDatabase

    override fun onCreate() {
        super.onCreate()
        appDatabase = WeatherDatabase.getInstance(this)
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        return true
    }

    @SuppressLint("StaticFieldLeak")
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("JOB", "STARTING JOB")
        launch {
            val list = ArrayList<WeatherData?>()
            try {
                list.add(WeatherData.getWeatherData("Montreal"))
                list.add(WeatherData.getWeatherData("Boisbriand"))
                list.add(WeatherData.getWeatherData("Tokyo"))
                list.add(WeatherData.getWeatherData("Laval"))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            val newList = list.filterNotNull()

            for( weather in newList){
                appDatabase.weatherDao().insertAll(weather)
            }
            Log.d("JOB", "FINISHING JOB")
            jobFinished(params, false)
        }
        return true
    }
}