package com.devnic.gmonitoring.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "MONTHS")
@Parcelize
data class ModelMonths(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDMONTHS")
    val idmonths: Long = 0L,
    @ColumnInfo(name = "YEAR")
    val year: Int,
    @ColumnInfo(name = "MONTHS")
    val months: Int,
    @ColumnInfo(name = "DAY")
    val day: Int,
    @ColumnInfo(name = "DESCRIPTION")
    val description: String
) : Parcelable
