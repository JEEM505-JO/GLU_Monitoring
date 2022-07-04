package com.devnic.gmonitoring.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "USER")
@Parcelize
data class ModelUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USER")
    val id: Long = 0L,
    @ColumnInfo(name = "USER_NAME")
    val username : String,
    @ColumnInfo(name = "EMAIL")
    val email : String,
    @ColumnInfo(name = "PASSWORD")
    val password : String
):Parcelable