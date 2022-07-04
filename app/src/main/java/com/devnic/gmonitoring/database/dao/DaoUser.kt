package com.devnic.gmonitoring.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devnic.gmonitoring.database.model.ModelUser

@Dao
interface DaoUser {

    @Insert
    suspend fun insert(user: ModelUser)

    @Query("SELECT * FROM USER WHERE USER_NAME = :user and PASSWORD = :pass")
    fun getUser(user: String, pass: String): List<ModelUser>

}