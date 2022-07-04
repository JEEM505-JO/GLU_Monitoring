package com.devnic.gmonitoring.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devnic.gmonitoring.database.model.ModelMonths
import kotlinx.coroutines.flow.Flow


@Dao
interface DaoMonths {

    @Query("SELECT * FROM MONTHS ORDER BY IDMONTHS DESC")
    fun getAllMonths() : Flow<List<ModelMonths>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonth(months: ModelMonths)

}