package com.tillylabs.okhttpdemo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.*

/**
 * Created by steven on 2017-10-13.
 * This is the weather data model for the list items in ListActivity
 */

@Entity
class WeatherData() {

    @PrimaryKey()
    @SerializedName("name")
    var cityName: String = ""

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String = ""
    @ColumnInfo(name = "current_temp")
    @SerializedName("temp")
    var currentTemp: Double = 0.0
    @ColumnInfo(name = "wind_speed")
    @SerializedName("speed")
    var windSpeed: Double = 0.0

    @ColumnInfo(name = "last_updated")
    @TypeConverters(RoomConverters::class)
    var timeStamp: Date = Date()

    @ColumnInfo(name = "icon")
    var icon: String = "04n"

    constructor(json: JSONObject?) : this(){
        if(json != null) {
            cityName = json.optString("name")
            description = json.optJSONArray("weather").optJSONObject(0).optString("description")
            currentTemp = json.optJSONObject("main").optDouble("temp")
            windSpeed = json.optJSONObject("wind").optDouble("speed")
            icon = json.optJSONArray("weather").optJSONObject(0).optString("icon")


        }
    }

    fun shouldRefreshData(): Boolean{
        val currentTime = Date()
        if(currentTime.time - timeStamp.time >= 1000L * 60L * 30L){ //30 minutes
            return true
        }
        return false
    }

    companion object {

        @JvmStatic
        fun getWeatherData(city: String): WeatherData?{
            val caller = NetworkCaller.instance
            val response = caller.run(city) ?: return null
            val json = JSONObject(response.toString())
            if(json.getInt("cod" ) == 200) {
                return WeatherData(json)
            }
            return null
        }
    }
}
