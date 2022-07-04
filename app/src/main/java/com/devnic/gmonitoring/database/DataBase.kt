package com.devnic.gmonitoring.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devnic.gmonitoring.database.dao.DaoMonths
import com.devnic.gmonitoring.database.dao.DaoUser
import com.devnic.gmonitoring.database.dao.DaoWeeks
import com.devnic.gmonitoring.database.model.ModelMonths
import com.devnic.gmonitoring.database.model.ModelUser
import com.devnic.gmonitoring.database.model.ModelWeeks

@Database(
    entities = [ModelMonths::class, ModelUser::class,ModelWeeks::class],
    version = 1, exportSchema = false
)
abstract class DataBaseM : RoomDatabase() {
    abstract fun daomonths(): DaoMonths
    abstract fun daoweeks() : DaoWeeks

    abstract fun daouser(): DaoUser

    companion object {
        @Volatile
        private var INSTANCE: DataBaseM? = null

        fun getDatabase(context: Context): DataBaseM {
            val tempinstance = INSTANCE
            if (tempinstance != null) {
                return tempinstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseM::class.java,
                    "DATA_BASE"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}