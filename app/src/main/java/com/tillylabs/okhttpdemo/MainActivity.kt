package com.tillylabs.okhttpdemo

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity



class MainActivity : AppCompatActivity(){

    lateinit var weatherVM: WeatherVM

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val db = Room.databaseBuilder(applicationContext,
                WeatherDatabase::class.java, "weather").fallbackToDestructiveMigration().build()
        setContentView(R.layout.activity_main)

    }
}
