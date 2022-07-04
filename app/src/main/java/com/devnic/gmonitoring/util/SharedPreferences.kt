package com.devnic.gmonitoring.util

import android.content.Context

class SharedPreferences(context: Context) {
    val SHARED = "Mydtb"
    val IDMONTH = "IDMONTH"
    val INIT = "INIT"
    val MONTH = "MONTH"
    val DAY = "DAY"

    val storage = context.getSharedPreferences(SHARED, 0)

    fun saveIDMonth(id: Long) {
        storage.edit().putLong(IDMONTH, id).apply()
    }

    fun getIDMonth(): Int {
       return storage.getInt(IDMONTH,0)
    }

    fun savemonth(month: Int) {
        storage.edit().putInt(IDMONTH, month).apply()
    }

    fun getmonth(): Int {
       return storage.getInt(MONTH,0)
    }

    fun saveday(day: Int) {
        storage.edit().putInt(DAY, day).apply()
    }

    fun getday(): Int {
        return storage.getInt(DAY,0)
    }

    fun deleteshared(){
        storage.edit().clear().commit()
    }
}