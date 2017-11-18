package com.tillylabs.okhttpdemo


import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by steven on 2017-11-16.
 */
object Utils {

    //Format: 12:00
    @JvmStatic
    fun unixToStringHour(unix: Long): String {
        try {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date(unix)
            var sDate = dateFormat.format(date)
            sDate = sDate.replace(":", "h")
            return sDate
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}