package com.tillylabs.okhttpdemo

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by steven on 2017-11-16.
 */
object RoomConverters {
    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}