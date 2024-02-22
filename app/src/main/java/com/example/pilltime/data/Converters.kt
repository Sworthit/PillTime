package com.example.pilltime.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.util.Date
import kotlin.collections.ArrayList

private fun <T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)


class Converters {
    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun gsonFromStringArrayList(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun gsonToStringArrayList(gson: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(gson)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}