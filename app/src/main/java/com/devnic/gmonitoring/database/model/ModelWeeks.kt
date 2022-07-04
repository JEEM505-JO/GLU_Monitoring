package com.devnic.gmonitoring.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "WEEKS")
@Parcelize
data class ModelWeeks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_WEEKS")
    var idweeks: Long = 0L,
    @ColumnInfo(name = "ID_MONTHS")
    val months: Long,
    @ColumnInfo(name = "INIT")
    val init: Int,
    @ColumnInfo(name = "FINISH")
    val finish: Int,
    @ColumnInfo(name = "TOTAL_DAY")
    val total_day: Int = 0
) : Parcelable
