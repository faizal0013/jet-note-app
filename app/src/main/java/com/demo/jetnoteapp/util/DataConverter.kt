package com.demo.jetnoteapp.util

import androidx.room.TypeConverter
import java.util.Date

class DataConverter {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long = date.time

    @TypeConverter
    fun dateFromTimeStamp(timeStamp: Long): Date? = Date(timeStamp)
}