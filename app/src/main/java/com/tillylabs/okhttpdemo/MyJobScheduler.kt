package com.tillylabs.okhttpdemo

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.util.Log

/**
 * Created by steven on 2017-11-16.
 */
class MyJobScheduler: JobService() {

    private lateinit var appDatabase: WeatherDatabase

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(application,
                WeatherDatabase::class.java, "weather").fallbackToDestructiveMigration().build()
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        return true
    }

    @SuppressLint("StaticFieldLeak")
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("JOB", "STARTING JOB")
        object : AsyncTask<Void, Void, Void>(){

            override fun doInBackground(vararg params: Void?): Void? {
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
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Log.d("JOB", "FINISHING JOB")
                jobFinished(params, false)
            }
        }.execute()
        return true
    }
}